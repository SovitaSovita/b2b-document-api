package kosign.b2bdocumentv4.payload.doc_tags;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocTagUpdateResponse {
    private Long id;
    private String title;
    private Long user_id;
    private Long status;
    // private String dep_id;
    // private String depId;
    private Long dept_id;
    private Timestamp create_date;
    private Timestamp modified_date;
}
