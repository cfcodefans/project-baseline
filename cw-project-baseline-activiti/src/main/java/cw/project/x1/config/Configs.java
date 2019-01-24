package cw.project.x1.config;

import cw.project.x1.component.ActivitiUserGroupMgr;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

@Configuration
@EnableSwagger2
//@Import(SpringDataRestConfiguration.class)
//@ImportResource("classpath:activiti/activiti.cfg.xml")
public class Configs implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    public String hostName;

    {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("X1 Service API")
            .description("api txType X1 service")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
            .host(hostName + ":8888")
            .forCodeGeneration(true)
            .protocols(Set.of("http"))
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("cw.project.x1.component.endpoint"))
            .paths(PathSelectors.any())
            .build();
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
}
