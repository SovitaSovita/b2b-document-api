package kosign.b2bdocumentv4.payload.doc_tags;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocTagUpdateRequest {
    private Long id;
    private String title;
    private String user_name;
    private Long status;
    private Long dept_id;
    //private Timestamp create_date;
    private Timestamp modified_date;
}
