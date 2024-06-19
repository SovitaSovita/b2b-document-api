package kosign.b2bdocumentv4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity {

     @CreatedBy
     @Column(updatable = false)
     private String createdBy;
     @LastModifiedBy
     private String modifiedBy;
     @CreatedDate
     @Column(updatable = false)
     private Timestamp create_date;
     @LastModifiedDate
     private Timestamp modified_date;

}
