package cw.project.x1.config;

import cw.project.x1.component.ActivitiUserGroupMgr;
import cw.project.x1.model.*;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
//@ImportResource("classpath:activiti/activiti.cfg.xml")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Configs implements WebMvcConfigurer {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(
                RepositoryRestConfiguration config) {
                config.exposeIdsFor(XUser.class,
                    XGroup.class,
                    XGroupAuthority.class,
                    XGroupMember.class,
                    XAuthority.class);
            }
        };
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.ofNullable(ud).map(UserDetails::getUsername);
        };
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }


//    @ConfigurationProperties(prefix = "file")
//    public static class FileStorageCfg {
//        public String uploadDir;
//    }

    @Autowired
    private ActivitiUserGroupMgr userManager;

    @Bean
    InitializingBean processEngineInitializer(SpringProcessEngineConfiguration processEngineConfiguration) {
        return () -> processEngineConfiguration.setUserGroupManager(userManager);
    }

    private static Logger log = LoggerFactory.getLogger(Configs.class);

}
