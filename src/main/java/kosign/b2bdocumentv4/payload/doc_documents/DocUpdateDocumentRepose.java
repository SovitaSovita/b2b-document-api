package kosign.b2bdocumentv4.payload.doc_documents;

import lombok.Data;

@Data
public class DocUpdateDocumentRepose {
    int dep_id;
    String dep_name;
    String dep_status;
    String modified_by;
    String modified_date;
}
