package kosign.b2bdocumentv4.entity.doc_request;

import kosign.b2bdocumentv4.dto.RequestMainItemsDto;
import kosign.b2bdocumentv4.dto.RequestToDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UpdateFormRequest {
    private String formContent;
    private List<RequestMainItemsDto> requestMainItems;

}
