package kosign.b2bdocumentv4.payload.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username","password"})
public class AuthenticationRequest{
    @NotBlank(message = "Username is required")
    String username ;
    @NotBlank(message = "Password is required")
    String password ;
}
