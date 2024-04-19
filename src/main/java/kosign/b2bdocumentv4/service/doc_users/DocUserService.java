package kosign.b2bdocumentv4.service.doc_users;


import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.doc_users.DocUserUpdateRequest;

public interface DocUserService {

    BaseResponse listUsers(Long dep_id);

    BaseResponse updateDocumentUser(DocUserUpdateRequest updateRequest);

}
