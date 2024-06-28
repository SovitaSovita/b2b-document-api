package kosign.b2bdocumentv4.payload.doc_form;

import lombok.Data;

@Data
public class GetFormRequest {
    private String userId;
    private int status;
}
