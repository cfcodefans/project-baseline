package cw.project.x1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "cw.project.x1.component")
@SpringBootApplication
public class X1App {
    private static Logger log = LoggerFactory.getLogger(X1App.class);

    private static ConfigurableApplicationContext appCtx;

    public static ConfigurableApplicationContext getAppCtx() {
        return appCtx;
    }

    //TODO Logic Below should be part txType another/different component
    public static void main(String... args) {
        // Moved the logic to StartupHook
        log.info("starting up X1App");
        try {
            appCtx = SpringApplication.run(X1App.class, args);
        } catch (Exception e) {
            log.error("failed to start", e);
        }
        log.info("finished");
    }
}
