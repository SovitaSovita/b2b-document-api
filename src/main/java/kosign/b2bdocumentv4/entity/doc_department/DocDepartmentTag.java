package kosign.b2bdocumentv4.entity.doc_department;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_department" , schema = "stdy")
public class DocDepartmentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dept_id;
    @NotNull
    private String dep_name;

    @NotNull
    private String dep_status;

    private String created_by;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String modified_by;

    @Column(name = "modified_date")
    private Timestamp modified_date;

    @Column(name = "create_date")
    private Timestamp create_date;


}
