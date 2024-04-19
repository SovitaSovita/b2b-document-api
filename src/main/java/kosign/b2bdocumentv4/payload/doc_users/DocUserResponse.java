package kosign.b2bdocumentv4.payload.doc_users;

import kosign.b2bdocumentv4.enums.Role;
import lombok.Data;

@Data
public class DocUserResponse {
    private Long id;
    private String username;
    private Role role;
}
