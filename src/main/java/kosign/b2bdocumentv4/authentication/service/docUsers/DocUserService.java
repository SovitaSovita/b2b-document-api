package kosign.b2bdocumentv4.authentication.service.docUsers;


import kosign.b2bdocumentv4.payload.BaseResponse;

public interface DocUserService {

    BaseResponse listUsers(Long dep_id);
}
