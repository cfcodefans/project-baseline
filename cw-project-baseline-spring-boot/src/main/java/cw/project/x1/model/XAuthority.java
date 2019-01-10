package cw.project.x1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "authorities ", indexes = @Index(columnList = "username,authority", unique = true))
public class XAuthority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    public XUser user;

    @Column(name = "authority", length = 50, nullable = false)
    public String authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XAuthority)) return false;
        XAuthority authority1 = (XAuthority) o;
        return user.equals(authority1.user) &&
            authority.equals(authority1.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, authority);
    }
}
