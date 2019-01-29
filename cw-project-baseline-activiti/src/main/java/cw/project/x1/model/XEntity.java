package cw.project.x1.model;

import cw.project.x1.commons.Jsons;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class XEntity implements Serializable {
    @Column(name = "metadata", length = 4096)
    public String metadata;

    @Column(name = "description", length = 4096)
    public String description;

    @CreatedDate
    @Temporal(TIMESTAMP)
    public Date created;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date updated;

    @LastModifiedBy
    protected String updatedBy;

    public String toString() {
        return Jsons.toString(this);
    }
}
