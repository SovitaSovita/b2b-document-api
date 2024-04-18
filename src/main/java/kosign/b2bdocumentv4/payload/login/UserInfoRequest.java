package kosign.b2bdocumentv4.payload.login;

import kosign.b2bdocumentv4.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    private String username;
    private String password;
    private Role role;
}
