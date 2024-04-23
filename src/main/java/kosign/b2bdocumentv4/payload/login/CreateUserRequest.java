package kosign.b2bdocumentv4.payload.login;

import kosign.b2bdocumentv4.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private Provider provider;
    private String username;
}
