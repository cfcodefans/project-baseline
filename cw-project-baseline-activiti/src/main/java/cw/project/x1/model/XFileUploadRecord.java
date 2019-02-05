package cw.project.x1.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "groups")
public class XFileUploadRecord extends XEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username", length = 50)
    public String username;

    @Column(name = "file_path", length = 2048)
    public String filePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XFileUploadRecord)) return false;
        XFileUploadRecord that = (XFileUploadRecord) o;
        return Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }

    public XFileUploadRecord(String username, String filePath) {
        this.username = username;
        this.filePath = filePath;
    }

    public XFileUploadRecord() {
    }
}
