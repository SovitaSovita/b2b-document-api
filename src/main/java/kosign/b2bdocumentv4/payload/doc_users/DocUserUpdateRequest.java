package kosign.b2bdocumentv4.payload.doc_users;

import kosign.b2bdocumentv4.enums.Role;
import lombok.Data;

@Data
public class DocUserUpdateRequest {
    private Long id;
    private String username;
    private Long status;
    private Role role;
    private String image;
    private Long dept_id;
}
