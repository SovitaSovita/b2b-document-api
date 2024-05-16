package kosign.b2bdocumentv4.payload.doc_documents;


import jakarta.persistence.Entity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocDepartmentRequest {
    //private  int dep_id;
    private  String dept_name;
    //private  String dep_status;
    private  String created_by;
}
