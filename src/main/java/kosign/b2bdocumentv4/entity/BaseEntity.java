package kosign.b2bdocumentv4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    // @CreatedBy
    // @Column(updatable = false)
    // private String createdBy;

    // @LastModifiedBy
    // private String modifiedBy;

    // @CreatedDate
    // @Column(updatable = false)
    // private Timestamp create_date;

    // @LastModifiedDate
    // private Timestamp modified_date;

    @Column(name = "create_date")
    private Timestamp create_date;

    @Column(name = "modified_date")
    private Timestamp modified_date;

    @PrePersist
    public void prePersist() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (create_date == null) {
            create_date = currentTimestamp;
        }
        modified_date = currentTimestamp;
    }

    @PreUpdate
    public void preUpdate() {
        modified_date = new Timestamp(System.currentTimeMillis());
    }
}
