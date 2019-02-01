package cw.project.x1.dto;

import cw.project.x1.model.XGroup;
import cw.project.x1.model.XGroupAuthority;
import cw.project.x1.model.XUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupDTO implements Serializable {
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
    public List<XGroupAuthority> authList = new ArrayList<>();
    public List<XUser> users = new ArrayList<>();

    public GroupDTO() {
    }

    public GroupDTO(XGroup group, List<XUser> users) {
        this.group = group;
        this.users = users;
    }
}
