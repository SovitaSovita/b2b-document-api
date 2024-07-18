package kosign.b2bdocumentv4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainItemsDto {
    private String itemName;
    private String type;
    private Boolean require;
    private List<ItemsDataDto> itemsData;
}
