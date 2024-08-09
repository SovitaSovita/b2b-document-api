package kosign.b2bdocumentv4.dto;

import kosign.b2bdocumentv4.entity.doc_request.RequestMainItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombineRequestFormDto {
    private Long id;
    private Long requestId;
    private Long formId;
    private String formName;
    private String classification;
    private String formContent;
    private String requestFrom;
    private String fromDepartment;
    private String fromCompany;
    private String requestTo;
    private String toDepartment;
    private String toCompany;
    private int reqOrder;
    private Timestamp requestDate;
    private String requestStatus;
    private List<RequestMainItems> requestMainItems;
    private String whoChecking;
    private String finalApprove;
}
