package kosign.b2bdocumentv4.service.doc_Department;

import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_documents.DocDepartmentRequest;
import kosign.b2bdocumentv4.payload.doc_documents.DocUpdateDocumentRequest;

public interface DocDepartmentService {
    BaseResponse ListDepartment();

    BaseResponse insertDepartment(DocDepartmentRequest documentDepartment);
    BaseResponse updateDepartment(DocUpdateDocumentRequest docUpdateDocumentRequest);
    BaseResponse deleteDepartment(long dept_id);
}
