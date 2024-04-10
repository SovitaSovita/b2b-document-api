package kosign.b2bdocumentv4.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class JwtRespon implements Serializable {
    private final LocalDateTime dateTime;
    private final String username;
    private final String token;
    private final Date expired;




}
