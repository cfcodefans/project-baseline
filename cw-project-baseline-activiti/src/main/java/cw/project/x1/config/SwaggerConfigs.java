package cw.project.x1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfigs {
    private static Logger log = LoggerFactory.getLogger(SwaggerConfigs.class);
    public String hostName;

    {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("failed to get hostname", e);
        }
    }

    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("X1 Service API")
            .description("api txType X1 service")
            .version("1.0")
            .build();

        String contextPath = hostName + ":8888";
        return new Docket(DocumentationType.SWAGGER_2)
            .host(contextPath)
            .forCodeGeneration(true)
            .protocols(Set.of("http"))
            .pathMapping("/")
            .apiInfo(apiInfo)
            .select()
            .apis(
                RequestHandlerSelectors.basePackage("cw.project.x1.component.endpoint")
                    .or(RequestHandlerSelectors.basePackage("cw.project.x1.component.repository"))
                    .or(RequestHandlerSelectors.withClassAnnotation(Repository.class))
            ).paths(PathSelectors.any())
            .build();
    }
}
