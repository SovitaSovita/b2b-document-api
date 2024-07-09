package kosign.b2bdocumentv4.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemsDataDto {
    private String itemName;
    private Boolean inputRequire;
    private String inputType;
    private String inputValue;
    private List<SubItemsDto> subItems;
}
