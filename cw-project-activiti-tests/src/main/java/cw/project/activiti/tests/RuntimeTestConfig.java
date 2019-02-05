package cw.project.activiti.tests;

import org.activiti.api.process.runtime.connector.Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
public class RuntimeTestConfig {
    public static final String EXPECTED_KEY = "expectedKey";
    public static boolean processImageConnectorExecuted = false;
    public static boolean tagImageConnectorExecuted = false;
    public static boolean discardImageConnectorExecuted = false;

    static final Logger log = Logger.getLogger(RuntimeTestConfig.class.getSimpleName());

    public static final String UDS = "myUserDetailsService";

    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        List<GrantedAuthority> salaboyAuthorities = List.of(
            new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"),
            new SimpleGrantedAuthority("GROUP_activitiTeam"));

        inMemoryUserDetailsManager.createUser(new User("salaboy",
            "password",
            salaboyAuthorities));

        List<GrantedAuthority> adminAuthorities = List.of(new SimpleGrantedAuthority("ROLE_ACTIVITI_ADMIN"));

        inMemoryUserDetailsManager.createUser(new User("admin", "password", adminAuthorities));

        List<GrantedAuthority> garthAuthorities = List.of(
            new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"),
            new SimpleGrantedAuthority("GROUP_doctor"));

        inMemoryUserDetailsManager.createUser(new User("garth", "password", garthAuthorities));

        return inMemoryUserDetailsManager;
    }

    public static final String PIC = "processImageConnector";

    @Bean
    public Connector processImageConnector() {
        return integrationCtx -> {
            Map<String, Object> inBoundVars = integrationCtx.getInBoundVariables();

            log.info("processImageConnector");
            log.info("My inbound variables keys: \n\t" + inBoundVars.keySet());
            log.info("My inbound variables values: \n\t" + inBoundVars.values());

            boolean expectedVal = (Boolean) inBoundVars.get(EXPECTED_KEY);

            integrationCtx.addOutBoundVariable("approved", expectedVal);

            processImageConnectorExecuted = true;

            return integrationCtx;
        };
    }

    public static final String TIC = "tagImageConnector";

    @Bean
    public Connector tagImageConnector() {
        return integrationCtx -> {
            Map<String, Object> inBoundVars = integrationCtx.getInBoundVariables();
            log.info("tagImageConnector");
            log.info("My inbound variables keys: \n\t" + inBoundVars.keySet());
            log.info("My inbound variables values: \n\t" + inBoundVars.values());
            tagImageConnectorExecuted = true;
            return integrationCtx;
        };
    }

    public static final String DIC = "discardImageConnector";

    @Bean
    public Connector discardImageConnector() {
        return integrationCtx -> {
            Map<String, Object> inBoundVars = integrationCtx.getInBoundVariables();
            log.info("discardImageConnector");
            log.info("My inbound variables keys: \n\t" + inBoundVars.keySet());
            log.info("My inbound variables values: \n\t" + inBoundVars.values());
            discardImageConnectorExecuted = true;
            return integrationCtx;
        };
    }
}
