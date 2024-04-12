package kosign.b2bdocumentv4.authentication.service.auth;


import kosign.b2bdocumentv4.authentication.auth.InfoChangePassword;
import kosign.b2bdocumentv4.model.entity.UserInfoDto;
import kosign.b2bdocumentv4.model.request.UserInfoRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthService extends UserDetailsService {

    void changePassword(Long id, InfoChangePassword password);

    UserInfoDto register(UserInfoRequest appUserRequest);
}
