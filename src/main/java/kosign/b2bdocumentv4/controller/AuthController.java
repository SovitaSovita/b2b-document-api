package kosign.b2bdocumentv4.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import kosign.b2bdocumentv4.payload.auth.AuthenticationRequest;
import kosign.b2bdocumentv4.payload.auth.AuthenticationResponse;
import kosign.b2bdocumentv4.payload.auth.InfoChangePassword;
import kosign.b2bdocumentv4.payload.login.CreateUserRequest;
import kosign.b2bdocumentv4.service.auth.AuthService;
import kosign.b2bdocumentv4.jwt.JwtRespon;
import kosign.b2bdocumentv4.jwt.JwtTokenUtils;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.dto.UserInfoDto;
import kosign.b2bdocumentv4.payload.login.UserInfoRequest;
import kosign.b2bdocumentv4.payload.login.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
    }


//    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest jwtRequest) {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        final UserDetails userDetails = authService.loadUserByUsername(jwtRequest.getUsername());
        AuthenticationResponse authResponse = authService.getUserByUsername(jwtRequest.getUsername());

        System.out.println(authResponse);

        final String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtRespon(LocalDateTime.now(),
                token,
                jwtTokenUtils.getExpirationDateFromToken(token),
                authResponse
        ));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
//            System.out.println(username+password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

//    @PutMapping("/change-password")
//    @Operation(summary = "change password")
//    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<?> changePassword(@Valid @RequestBody InfoChangePassword changePassword){
        DocumentUsers currentUser = (DocumentUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("[DEBUG] " + currentUser);
        authService.changePassword(currentUser.getId(),changePassword);
        return ApiResponse.builder()
                .date(LocalDateTime.now())
                .message("successfully change password")
                .build();
    }

//    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserInfoRequest appUserRequest){
        UserInfoDto appUserDto = authService.register(appUserRequest);
        ApiResponse<UserInfoDto> response = ApiResponse.<UserInfoDto>builder()
                .status(200)
                .message("Registered Successfully!")
                .payload(appUserDto)
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

//    @DeleteMapping("/delete_user")
    public ResponseEntity<?> deleteUser(@RequestParam Long user_id){
        authService.deleteUser(user_id);
        ApiResponse<DocumentUsers> response = ApiResponse.<DocumentUsers>builder()
                .status(200)
                .message("Deleted Successfully!")
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
}

