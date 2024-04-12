package kosign.b2bdocumentv4.service.auth;

import kosign.b2bdocumentv4.payload.auth.InfoChangePassword;
import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.enums.Role;
import kosign.b2bdocumentv4.dto.UserInfoDto;
import kosign.b2bdocumentv4.payload.login.UserInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

//    @PostConstruct
    public void createAdmin() {
        System.out.println("TEST TEST");
        System.out.println(authRepository.findAll());
       if (authRepository.findAll().isEmpty()){
           DocumentUsers documentUsers = new DocumentUsers();
           documentUsers.setUsername(adminUsername);
           documentUsers.setRole(Role.ADMIN);
           String pass = passwordEncoder.encode(adminPassword);
           documentUsers.setPassword(pass);
           authRepository.save(documentUsers);
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
    public void changePassword(Long id, InfoChangePassword password) {
        Optional<DocumentUsers> adminOptional = authRepository.findById(id);
        if (adminOptional.isPresent()) {
            DocumentUsers documentUsers = adminOptional.get();
            String pass = passwordEncoder.encode(password.getCurrentPassword());
            String newpass = passwordEncoder.encode(password.getNewPassword());

            if (!passwordEncoder.matches(password.getCurrentPassword(), documentUsers.getPassword())) {
                throw new IllegalArgumentException("Current Password isn't correct. Please Try Again.");
            }

            if (passwordEncoder.matches(password.getNewPassword(), documentUsers.getPassword())) {
                throw new IllegalArgumentException("Your new password is still the same as your old password");
            }

            if (!password.getNewPassword().equals(password.getConfirmPassword())) {
                throw new IllegalArgumentException("Your confirm password does not match with your new password");
            }

            documentUsers.setPassword(newpass);
            authRepository.save(documentUsers);
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
        appUserRequest.setRole(appUserRequest.getRole());
        DocumentUsers appUser = mapper.map(appUserRequest, DocumentUsers.class);

        authRepository.save(appUser);
        //return as user_dto
        return mapper.map(appUser, UserInfoDto.class);
    }




}
