package kosign.b2bdocumentv4.payload.docUser;

import lombok.Data;

@Data
public class DocUserResponse {
    private Long id;
    private String username;
    private String role;
}
