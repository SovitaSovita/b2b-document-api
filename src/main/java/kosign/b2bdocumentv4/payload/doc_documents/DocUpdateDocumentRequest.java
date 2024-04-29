package kosign.b2bdocumentv4.payload.doc_documents;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocUpdateDocumentRequest {
    private Long dept_id;
    private String dept_name;
    private String dept_status;
    private String modified_by;
    //private String modified_date;
    //private Timestamp create_at;
}
