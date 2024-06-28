package kosign.b2bdocumentv4.dto;

import lombok.Data;

@Data
public class ItemsDataDto {
    private String itemName;
    private Boolean inputRequire;
    private String inputType;
    private String inputValue;
    private boolean isSelected;
}
