package kosign.b2bdocumentv4.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse{
    Integer userId;
    String username;
    String token;
}