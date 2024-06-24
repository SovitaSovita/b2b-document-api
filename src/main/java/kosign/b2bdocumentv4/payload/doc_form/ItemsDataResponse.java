package kosign.b2bdocumentv4.payload.doc_form;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kosign.b2bdocumentv4.entity.doc_form.Form;
import lombok.Data;

@Data
public class ItemsDataResponse {
    private Long id;
    private String itemName;
    private Boolean inputRequire;
    private String inputType;
    private String inputValue; //text
    private Form form;
}
