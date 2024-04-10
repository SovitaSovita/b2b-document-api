package kosign.b2bdocumentv4.authentication.service.auth;

import jakarta.annotation.PostConstruct;
import kosign.b2bdocumentv4.authentication.auth.InfoChangePassword;
import kosign.b2bdocumentv4.authentication.repository.AuthRepository;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.model.entity.UserInfo;
import kosign.b2bdocumentv4.model.entity.UserInfoDto;
import kosign.b2bdocumentv4.model.request.UserInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper = new ModelMapper();


    @Value("${myAdmin.username}")
    private String adminUsername;
    @Value("${myAdmin.password}")
    private String adminPassword;

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createAdmin() {
       if (authRepository.findAll().isEmpty()){
           UserInfo userInfo = new UserInfo();
           userInfo.setUsername(adminUsername);
           String pass = passwordEncoder.encode(adminPassword);
           userInfo.setPassword(pass);
           authRepository.save(userInfo);
       }else{
           System.out.println("already have admin");
       }
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserDetails userDetails = authRepository.findByUsername(username);
        return userDetails;
    }

    @Override
    public void changePassword(UUID id, InfoChangePassword password) {
        Optional<UserInfo> adminOptional = authRepository.findById(id);
        if (adminOptional.isPresent()) {
            UserInfo userInfo = adminOptional.get();
            String pass = passwordEncoder.encode(password.getCurrentPassword());
            String newpass = passwordEncoder.encode(password.getNewPassword());

            if (!passwordEncoder.matches(password.getCurrentPassword(), userInfo.getPassword())) {
                throw new IllegalArgumentException("Current Password isn't correct. Please Try Again.");
            }

            if (passwordEncoder.matches(password.getNewPassword(), userInfo.getPassword())) {
                throw new IllegalArgumentException("Your new password is still the same as your old password");
            }

            if (!password.getNewPassword().equals(password.getConfirmPassword())) {
                throw new IllegalArgumentException("Your confirm password does not match with your new password");
            }

            userInfo.setPassword(newpass);
            authRepository.save(userInfo);
        } else {
            throw new NotFoundExceptionClass("Admin not found with ID: " + id);
        }
    }

    public UserInfoDto register(UserInfoRequest appUserRequest) {
        if (authRepository.existsByUsername(appUserRequest.getUsername())) {
            throw new NotFoundExceptionClass("username is already in use.");
        }
        //set pw to encoder
        String pass = passwordEncoder.encode(appUserRequest.getPassword());
        appUserRequest.setPassword(pass);
        UserInfo appUser = mapper.map(appUserRequest, UserInfo.class);

        authRepository.save(appUser);
        //return as userDTO
        return mapper.map(appUser, UserInfoDto.class);
    }




}
