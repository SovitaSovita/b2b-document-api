package kosign.b2bdocumentv4.entity.doc_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_request" , schema = "stdy")
public class RequestForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long requestId;

    private Long formId;

    private String formContent;
    private String requestFrom;
    private String requestTo;
    private Timestamp requestDate;

    @Enumerated(EnumType.STRING)
    private RqStatus requestStatus; //enum

    @OneToMany(mappedBy = "requestForm", cascade = CascadeType.ALL)
    private List<RequestItemsData> requestItemsData;


    @Override
    public String toString() {
        return "RequestForm{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", formId=" + formId +
                ", formContent='" + formContent + '\'' +
                ", requestFrom='" + requestFrom + '\'' +
                ", requestTo='" + requestTo + '\'' +
                ", requestDate=" + requestDate +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
