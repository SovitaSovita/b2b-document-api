package kosign.b2bdocumentv4.payload.doc_documents;

import lombok.Data;

@Data
public class DocUpdateDocumentRepose {
    int dep_id;
    String dept_name;
    String dept_status;
    String modified_by;
    String modified_date;
}
