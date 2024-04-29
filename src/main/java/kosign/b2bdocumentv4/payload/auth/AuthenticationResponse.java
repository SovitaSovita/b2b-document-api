package kosign.b2bdocumentv4.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse{
    private String username;
    private Long dept_id;
    private String image;
    private Long status;
}
