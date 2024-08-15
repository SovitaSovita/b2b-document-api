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
    private String formName;
    private String classification;
    private String formContent;

    private String requestFrom;
    private String fromDepartment;
    private String fromPosition;
    private String fromCompany;

    private String requestTo;
    private String toDepartment;
    private String toPosition;
    private String toCompany;

//    private String reference;

    private Integer reqOrder;
    private Timestamp requestDate;
    private Timestamp modifiedDate;
    private Timestamp approveDate;

    @Enumerated(EnumType.STRING)
    private RqStatus requestStatus; //enum

    @OneToMany(mappedBy = "requestForm", cascade = CascadeType.ALL)
    private List<RequestMainItems> requestMainItems;

    @Override
    public String toString() {
        return "RequestForm{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", formId=" + formId +
                ", formName=" + formName +
                ", classification='" + classification + '\'' +
                ", formContent='" + formContent + '\'' +
                ", requestFrom='" + requestFrom + '\'' +
                ", fromDepartment='" + fromDepartment + '\'' +
                ", fromPosition='" + fromPosition + '\'' +
                ", fromCompany='" + fromCompany + '\'' +
                ", requestTo='" + requestTo + '\'' +
                ", toDepartment='" + toDepartment + '\'' +
                ", toPosition='" + toPosition + '\'' +
                ", toCompany='" + toCompany + '\'' +
                ", reqOrder'" + reqOrder + '\'' +
                ", requestDate=" + requestDate + '\'' +
                ", modifiedDate=" + modifiedDate + '\'' +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
