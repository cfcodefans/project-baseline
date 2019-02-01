package cw.project.x1.dto;

import cw.project.x1.model.XGroup;
import cw.project.x1.model.XGroupAuthority;
import cw.project.x1.model.XUser;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserDTO implements Serializable {
    public XGroup group;
    public XUser user;
    public List<XGroupAuthority> authList;

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

    public UserDTO(XGroup group, XUser user, List<XGroupAuthority> authList) {
        this.group = group;
        this.user = user;
        this.authList = authList;
    }
}
