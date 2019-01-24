package cw.project.x1.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class XContact {

    @Column(name = "real_name", length = 10)
    public String realName;

    @Column(name = "email", length = 64)
    public String email;

    @Column(name = "phone", length = 20)
    public String phone;

    @Column(name = "icon", length = 100)
    public String iconUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XContact)) return false;
        XContact xContact = (XContact) o;
        return Objects.equals(phone, xContact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
