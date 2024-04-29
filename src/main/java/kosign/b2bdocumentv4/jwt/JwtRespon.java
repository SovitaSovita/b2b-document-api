package kosign.b2bdocumentv4.jwt;

import kosign.b2bdocumentv4.payload.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class JwtRespon implements Serializable {
    private final LocalDateTime dateTime;
    private final String token;
    private final Date expired;
    private final AuthenticationResponse authenticationResponse;




}
