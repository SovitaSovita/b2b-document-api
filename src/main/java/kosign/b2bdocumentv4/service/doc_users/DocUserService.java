package kosign.b2bdocumentv4.service.doc_users;


import kosign.b2bdocumentv4.payload.BaseResponse;

public interface DocUserService {

    BaseResponse listUsers(Long dep_id);
}