package cw.project.x1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AuthenticationEntryPoint authEntryPoint;

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
            .antMatchers("/rest/")
            .authenticated()
            .anyRequest().permitAll()
            .and()
            .logout().permitAll()
            .and()
            .formLogin().loginPage("/login")
            .and()
            .csrf().disable();
    }

    @Autowired
    DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager uds = auth
            .jdbcAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(ds).getUserDetailsService();

        uds.setEnableAuthorities(true);
        uds.setEnableGroups(true);
    }
}
