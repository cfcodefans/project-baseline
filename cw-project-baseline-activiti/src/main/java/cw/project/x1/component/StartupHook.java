package cw.project.x1.component;

import cw.project.x1.component.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static cw.project.x1.config.SpringSecurityConfig.Groups.GROUP_ADMIN;

@Component
public class StartupHook implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger log = LoggerFactory.getLogger(StartupHook.class);

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Autowired
    UserRepo ur;
//    @Autowired
//    GroupRepo gr;

    void tryToInitiateGroupsAndUsers() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        long userCount = ur.count();
        if (userCount == 0) {
            log.info("\n\tthere is not user\n\tgoing to create default users");
        }

        List<GrantedAuthority> adminAuthority = List.of(new SimpleGrantedAuthority(GROUP_ADMIN.name()));
        List<String> grpList = userDetailsService.findAllGroups();
        if (CollectionUtils.isEmpty(grpList)) {
            log.info("\n\tgoing to create default groups");
            userDetailsService.createGroup(GROUP_ADMIN.name(), adminAuthority);
        }
        List<String> uList = userDetailsService.findUsersInGroup(GROUP_ADMIN.name());
        if (CollectionUtils.isEmpty(uList)) {
            log.info("\n\tgoing to create default user");
            String username = "x1";
            userDetailsService.createUser(new User(username, passwordEncoder.encode("x1"), adminAuthority));
            userDetailsService.addUserToGroup(username, GROUP_ADMIN.name());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        tryToInitiateGroupsAndUsers();
    }
}
