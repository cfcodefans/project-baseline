package cw.project.x1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class XUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username", length = 50)
    public String username;

    @Column(name = "password", length = 50, unique = true, nullable = false)
    public String password;

    @Column(name = "enable", nullable = false)
    public Boolean enable = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XUser)) return false;
        XUser user = (XUser) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
