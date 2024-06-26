package kosign.b2bdocumentv4.service.auth;

import kosign.b2bdocumentv4.enums.Provider;
import kosign.b2bdocumentv4.payload.auth.AuthenticationResponse;
import kosign.b2bdocumentv4.payload.auth.InfoChangePassword;
import kosign.b2bdocumentv4.entity.doc_users.AuthRepository;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.enums.Role;
import kosign.b2bdocumentv4.dto.UserInfoDto;
import kosign.b2bdocumentv4.payload.login.CreateUserRequest;
import kosign.b2bdocumentv4.payload.login.UserInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper = new ModelMapper();

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return authRepository.findByUsername(username);
    }

    @Override
    public void changePassword(Long id, InfoChangePassword password) {
        Optional<DocumentUsers> adminOptional = authRepository.findById(id);
//        System.out.println("[DEBUG] :: " + adminOptional.get());
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

//            documentUsers.setPassword(newpass);
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
        appUserRequest.setRole(appUserRequest.getRole());
        DocumentUsers appUser = mapper.map(appUserRequest, DocumentUsers.class);
        appUser.setStatus(1L);
        appUser.setDept_id(1L);

        authRepository.save(appUser);
        //return as user_dto
        return mapper.map(appUser, UserInfoDto.class);
    }

    @Override
    public void deleteUser(Long user_id) {
        DocumentUsers user = authRepository.findById(user_id).orElseThrow(() -> new NotFoundExceptionClass("User with id [" + user_id + "] not found."));
        authRepository.deleteById(user.getId());
    }

    @Override
    public AuthenticationResponse getUserByUsername(String username) {
        return mapper.map(authRepository.findByUsername(username), AuthenticationResponse.class);
    }
}
