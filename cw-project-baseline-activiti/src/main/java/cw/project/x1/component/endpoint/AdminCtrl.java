package cw.project.x1.component.endpoint;

import cw.project.x1.commons.ServiceRespDTO;
import cw.project.x1.component.repository.GroupRepo;
import cw.project.x1.component.repository.UserRepo;
import cw.project.x1.config.SpringSecurityConfig;
import cw.project.x1.model.XGroup;
import cw.project.x1.model.XUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@RestController
@RequestMapping(path = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("AdminCtrl")
public class AdminCtrl {
    private static Logger log = LoggerFactory.getLogger(AdminCtrl.class);

    @Autowired
    @Qualifier(SpringSecurityConfig.BN_USER_DETAILS_MGR)
    private JdbcUserDetailsManager judm;

    @Autowired
    private UserRepo ur;
    @Autowired
    private GroupRepo gr;

    @ApiOperation("admin api endpoint info")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/info")
    public String info() {
        return format("admin api endpoint responded at %s", Date.from(Instant.now()));
    }

    public static class GroupDTO implements Serializable {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GroupDTO)) return false;
            GroupDTO groupDTO = (GroupDTO) o;
            return Objects.equals(group, groupDTO.group);
        }

        @Override
        public int hashCode() {
            return Objects.hash(group);
        }

        public XGroup group;
        public List<XUser> users = new ArrayList<>();

        public GroupDTO() {
        }

        public GroupDTO(XGroup group, List<XUser> users) {
            this.group = group;
            this.users = users;
        }
    }

    public static class UserDTO implements Serializable {
        public XGroup group;
        public XUser user;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserDTO)) return false;
            UserDTO userDTO = (UserDTO) o;
            return Objects.equals(user, userDTO.user);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user);
        }

        public UserDTO() {
        }

        public UserDTO(XGroup group, XUser user) {
            this.group = group;
            this.user = user;
        }
    }

    @ApiOperation("get all users by group")
    @GetMapping(path = "/groups")
    public ServiceRespDTO<List<GroupDTO>> getUsersByGroups(@RequestParam("grps") String[] groups) {
        return new ServiceRespDTO<>(
            ur.findUserAndGroups(groups).entrySet()
                .stream()
                .map(en -> new GroupDTO(en.getKey(), en.getValue()))
                .collect(Collectors.toList()));
    }

    @ApiOperation("Add users into group")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/groups/{group-name}")
    public ServiceRespDTO<GroupDTO> addUsersIntoGroup(@PathVariable("group-name") @NotBlank String groupName,
                                                      @RequestBody XUser[] users) {

        XGroup g = gr.findByName(groupName);
        if (g == null) {
            throw new NoSuchElementException(format("group % is not found", groupName));
        }

        if (!ArrayUtils.isEmpty(users)) {
            Stream.of(users).forEach(user -> judm.addUserToGroup(user.username, groupName));
        }

        return new ServiceRespDTO<>(
            ur.findUserAndGroups(groupName).entrySet()
                .stream()
                .map(en -> new GroupDTO(en.getKey(), en.getValue()))
                .findFirst().orElseThrow()
        );
    }

    @ApiOperation("remove users from group")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/groups/{group-name}")
    public ServiceRespDTO<GroupDTO> remveUsersFromGroup(@PathVariable("group-name") @NotBlank String groupName,
                                                        @RequestBody XUser[] users) {

        XGroup g = gr.findByName(groupName);
        if (g == null) {
            throw new NoSuchElementException(format("group % is not found", groupName));
        }

        if (!ArrayUtils.isEmpty(users)) {
            Stream.of(users).forEach(user -> judm.removeUserFromGroup(user.username, groupName));
        }

        return new ServiceRespDTO<>(
            ur.findUserAndGroups(groupName).entrySet()
                .stream()
                .map(en -> new GroupDTO(en.getKey(), en.getValue()))
                .findFirst().orElseThrow()
        );
    }

    @ApiOperation("create user")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/groups/{group-name}/save-user")
    public ServiceRespDTO<UserDTO> save(@PathVariable("group-name") @NotBlank String groupName,
                                        @RequestBody XUser user) {
        XGroup g = gr.findByName(groupName);
        if (g == null) {
            throw new NoSuchElementException(format("group %s is not found", groupName));
        }

        XUser saved = ur.saveAndFlush(user);
        return new ServiceRespDTO<>(new UserDTO(g, saved));
    }
}
