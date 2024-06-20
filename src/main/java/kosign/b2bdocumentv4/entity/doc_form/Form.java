package kosign.b2bdocumentv4.entity.doc_form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_form" , schema = "stdy")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String formName;
    private String formContent;
    private String username;
    private int status = 1; //0 = default, 1 = created by user
    private Timestamp createDate;
}
