package kosign.b2bdocumentv4.authentication.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonPropertyOrder({"username","currentPassword","newPassword"})
public class InfoChangePassword {
    @NotBlank(message = "Password is required")
    private String currentPassword;
    @NotBlank(message = "Password is required")
    private String newPassword;
    @NotBlank(message = "Password is required")
    private String confirmPassword;

}
