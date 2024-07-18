package kosign.b2bdocumentv4.entity.doc_form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

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
    private String classification;
    private String formName;
    private String formDescription;
    private String formContent;
    private String formNumber;
    private String isItem;// use, unused


    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<MainItems> mainItems;

    private String fileId;
    private String username;
    private String company;
    private int status = 1; //0 = none, 1 = default , 2 = created by user
    private Timestamp createDate;

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", classification='" + classification + '\'' +
                ", formName='" + formName + '\'' +
                ", formDescription='" + formDescription + '\'' +
                ", formContent='" + formContent + '\'' +
                ", formNumber='" + formNumber + '\'' +
                ", isItem='" + isItem + '\'' +
                ", fileId='" + fileId + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                '}';
    }
}
