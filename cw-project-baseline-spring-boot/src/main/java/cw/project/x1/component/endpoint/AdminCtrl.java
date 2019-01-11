package cw.project.x1.component.endpoint;

import cw.project.x1.component.repository.UserRepo;
import cw.project.x1.config.SpringSecurityConfig;
import cw.project.x1.model.XGroup;
import cw.project.x1.model.XUser;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/admin")
public class AdminCtrl {
    private static Logger log = LoggerFactory.getLogger(AdminCtrl.class);

    @Autowired
    @Qualifier(SpringSecurityConfig.BN_USER_DETAILS_MGR)
    private JdbcUserDetailsManager judm;

    @Autowired
    private UserRepo ur;

    @ApiOperation("admin api endpoint info")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/info")
    public String info() {
        return format("admin api endpoint responded at %s", Date.from(Instant.now()));
    }

    @ApiOperation("get all users by group")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/users")
    public Map<XGroup, List<XUser>> getUsersByGroups(@RequestParam("grps") String[] groups) {
        return ur.findUserAndGroups(groups);
    }

}
