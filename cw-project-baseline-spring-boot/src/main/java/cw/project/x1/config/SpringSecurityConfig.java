package cw.project.x1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

import static cw.project.x1.config.SpringSecurityConfig.Role.admin;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String BN_USER_DETAILS_MGR = "user-details-mgr";
    private JdbcUserDetailsManager userDetailsService;

    private static Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    enum Role {
        admin, operator
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf()
//            .disable()
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .httpBasic()
//            .authenticationEntryPoint(authEntryPoint);

        http.authorizeRequests()
            .antMatchers("/api/admin/**").authenticated()
            .anyRequest().hasAuthority("admin")
            .antMatchers("/api/operator/**").authenticated()
            .anyRequest().hasAnyAuthority("admin", "operator")
            .anyRequest().permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
            .and()
            .formLogin().defaultSuccessUrl("/", true).permitAll()
            .and()
            .csrf().disable();
    }

    @Autowired
    DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> judsCfg = auth
            .jdbcAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(ds);

        userDetailsService = judsCfg.getUserDetailsService();
        userDetailsService.setEnableAuthorities(false);
        userDetailsService.setEnableGroups(true);

        List<GrantedAuthority> adminAuthority = List.of(new SimpleGrantedAuthority(admin.name()));
        List<String> grpList = userDetailsService.findAllGroups();
        if (CollectionUtils.isEmpty(grpList)) {
            log.info("\n\tgoing to create default groups");
            userDetailsService.createGroup(admin.name(), adminAuthority);
        }
        List<String> uList = userDetailsService.findUsersInGroup(admin.name());
        if (CollectionUtils.isEmpty(uList)) {
            log.info("\n\tgoing to create default user");
            String username = "x1";
            userDetailsService.createUser(new User(username, "x1", adminAuthority));
            userDetailsService.addUserToGroup(username, Role.admin.name());
        }
    }

    @Bean(name = BN_USER_DETAILS_MGR)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JdbcUserDetailsManager getUserDetailsService() {
        return userDetailsService;
    }
}
