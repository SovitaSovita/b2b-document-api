package kosign.b2bdocumentv4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMainItemsDto {
    private String value;
    private List<RequestItemsDataDto> requestItemsData;
}
