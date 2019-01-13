package cw.project.x1.commons;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ServiceDTO implements Serializable {

    public String id;
    public Date time = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceDTO)) return false;
        ServiceDTO that = (ServiceDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
