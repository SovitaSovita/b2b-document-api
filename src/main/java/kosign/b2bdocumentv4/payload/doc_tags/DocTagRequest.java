package kosign.b2bdocumentv4.payload.doc_tags;

import lombok.Data;

@Data
public class DocTagRequest {
    private String title;
    private Long user_id;
    private String dept_id;
}
