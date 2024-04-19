package kosign.b2bdocumentv4.service.auth;


import kosign.b2bdocumentv4.payload.auth.InfoChangePassword;
import kosign.b2bdocumentv4.dto.UserInfoDto;
import kosign.b2bdocumentv4.payload.login.UserInfoRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthService extends UserDetailsService {

    void changePassword(Long id, InfoChangePassword password);

    UserInfoDto register(UserInfoRequest appUserRequest);

    void deleteUser(Long user_id);
}
