package cw.project.x1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
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

import java.util.Set;

@Configuration
@EnableSwagger2
public class Configs implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/").setViewName("/frontend/home");
    }

    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("X1 Service API")
            .description("api txType X1 service")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
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
}
