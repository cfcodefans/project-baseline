package cw.project.activiti.tests;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RuntimeConfigTests {

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private UserGroupManager userGroupManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @WithUserDetails(value = "salaboy", userDetailsServiceBeanName = "myUserDetailsService")
    public void validatingConfigForUser() {
        String authenticatedUserId = securityManager.getAuthenticatedUserId();
        assertNotNull(authenticatedUserId);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticatedUserId);
        assertNotNull(userDetails);

        assertEquals(userDetails.getAuthorities().size(), 2);

        List<String> userRoles = userGroupManager.getUserRoles(authenticatedUserId);
        assertNotNull(userRoles);
        assertEquals(userRoles.size(), 1);
        assertEquals(userRoles.get(0), "ACTIVITI_USER");
        List<String> userGroups = userGroupManager.getUserGroups(authenticatedUserId);
        assertNotNull(userGroups);
        assertEquals(userGroups.size(), 1);
        assertEquals(userGroups.get(0), "activitiTeam");
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "myUserDetailsService")
    public void validatingConfigForAdmin() {
        String authenticatedUserId = securityManager.getAuthenticatedUserId();
        assertNotNull(authenticatedUserId);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticatedUserId);
        assertNotNull(userDetails);

        assertEquals(userDetails.getAuthorities().size(), 1);
        List<String> userRoles = userGroupManager.getUserRoles(authenticatedUserId);
        assertNotNull(userRoles);
        assertEquals(userRoles.size(), 1);
        assertEquals(userRoles.get(0), "ACTIVITI_ADMIN");
        List<String> userGroups = userGroupManager.getUserGroups(authenticatedUserId);
        assertNotNull(userGroups);
        assertEquals(userGroups.size(), 0);
    }
}
