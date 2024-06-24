package kosign.b2bdocumentv4.payload.doc_form;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import kosign.b2bdocumentv4.entity.doc_form.ItemsData;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class FormResponse {
    private Long id;
    private String classification;
    private String formName;
    private String formDescription;
    private String formContent;
    private String formNumber;
    private String isItem;// use, unused
    private List<ItemsDataResponse> itemsData;
    private String fileId;
    private String username;
    private int status = 1; //0 = default, 1 = created by user
    private Timestamp createDate;
}
