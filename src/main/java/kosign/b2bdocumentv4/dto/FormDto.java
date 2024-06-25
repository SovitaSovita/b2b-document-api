package kosign.b2bdocumentv4.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class FormDto {
    private String classification;
    private String formName;
    private String formDescription;
    private String formContent;
    private String formNumber;
    private String isItem;
    private List<ItemsDataDto> itemsData;
    private String fileId;
    private String username;
    private Timestamp createDate;
}
