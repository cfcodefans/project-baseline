package cw.project.x1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "group_members")
public class XGroupMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "username", length = 50, nullable = false)
    public String username;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    public XGroup group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XGroupMember)) return false;
        XGroupMember that = (XGroupMember) o;
        if (id != null)
            return Objects.equals(id, that.id);

        return Objects.equals(username, that.username) &&
            Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        if (id != null)
            return Objects.hash(id);
        return Objects.hash(username, group);
    }
}
