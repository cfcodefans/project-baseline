package cw.project.x1.component;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class ActivitiUserGroupMgr implements UserGroupManager {

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Override
    public List<String> getUserGroups(String username) {
        return userDetailsService.loadUserByUsername(username).getAuthorities().stream()
            .filter((GrantedAuthority a) -> a.getAuthority().startsWith("GROUP_"))
            .map((GrantedAuthority a) -> a.getAuthority().substring(6))
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getUserRoles(String username) {
        return userDetailsService.loadUserByUsername(username).getAuthorities().stream()
            .filter((GrantedAuthority a) -> a.getAuthority().startsWith("ROLE_"))
            .map((GrantedAuthority a) -> a.getAuthority().substring(5))
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups() {
        return userDetailsService.findAllGroups();
    }

    @Override
    public List<String> getUsers() {
        return getGroups().stream().map(userDetailsService::findUsersInGroup)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
