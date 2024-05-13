package kosign.b2bdocumentv4.entity.doc_tags;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kosign.b2bdocumentv4.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_tags", schema = "stdy")
public class DocumentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @NotBlank
    @NotEmpty
    private String user_name;

    @NotBlank
    @NotEmpty
    private Long status;

    private Long dept_id;

    @Column(name = "create_date")
    private Timestamp create_date;


    private Timestamp modified_date;

    @PrePersist
    protected void onCreate() {
        create_date     = new Timestamp(System.currentTimeMillis());

    }
}
