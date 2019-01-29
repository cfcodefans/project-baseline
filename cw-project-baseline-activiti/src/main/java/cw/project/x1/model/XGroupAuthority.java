package cw.project.x1.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_authorities")
public class XGroupAuthority extends XEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    public XGroup group;

    @Column(name = "authority", length = 50, nullable = false)
    public String authority;

    @Column(name = "description", length = 4096)
    public String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XGroupAuthority)) return false;
        XGroupAuthority that = (XGroupAuthority) o;
        return Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }
}
