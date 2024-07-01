package kosign.b2bdocumentv4.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RequestFormDto {
    private Long formId;
    private String formContent;
    private String requestFrom;
    private String requestTo;
    private Timestamp requestDate;
    private List<RequestItemsDataDto> requestItemsData;
}
