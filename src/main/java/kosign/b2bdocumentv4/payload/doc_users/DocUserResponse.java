package kosign.b2bdocumentv4.payload.doc_users;

import kosign.b2bdocumentv4.enums.Role;
import lombok.Data;

@Data
public class DocUserResponse {
    private Long id;
    private String username;
    private String image;
    private Long status;
    private Long dept_id;
    private Role role;
}
