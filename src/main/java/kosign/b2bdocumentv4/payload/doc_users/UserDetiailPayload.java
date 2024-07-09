package kosign.b2bdocumentv4.payload.doc_users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetiailPayload {
    private String username;
    private String company;
    private String department;
    private String departmentId;
    private String phoneNumber;
    private String position;
    private String email;
    private String profile;
    private String fullName;
}
