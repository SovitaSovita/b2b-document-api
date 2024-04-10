package kosign.b2bdocumentv4.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kosign.b2bdocumentv4.configuration.ValidationConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_users", schema = "stdy")
public class DocUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    private String username;

    @NotBlank(message = ValidationConfig.PASSWORD_REQUIRED_MESSAGE)
    @Size(min = ValidationConfig.PASSWORD_VALIDATION_MIN, message = ValidationConfig.PASSWORD_RESPONSE_MESSAGE)
    @Pattern(regexp = ValidationConfig.PASSWORD_VALIDATION_REG, message = ValidationConfig.PASSWORD_RESPONSE_REG_MESSAGE)
    private String password;

    private Long role;

    @Column(name = "dept_id")
    private Long dept_id;

    private String image;

}
