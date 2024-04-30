package kosign.b2bdocumentv4.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsers;
import kosign.b2bdocumentv4.entity.doc_users.DocumentUsersRepository;
import kosign.b2bdocumentv4.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
@Service
public class JwtTokenUtils implements Serializable {

    private final WebClient.Builder webClient;
    @Value("${API_URL}")
    private String API_URL;

    private final DocumentUsersRepository documentUsersRepository;

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenUtils(WebClient.Builder webClient, DocumentUsersRepository documentUsersRepository) {
        this.webClient = webClient;
        this.documentUsersRepository = documentUsersRepository;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
}

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String usernameFromToken = getUsernameFromToken(token);
        String json = getUserDetails(usernameFromToken).block();

        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode payloadNode = jsonNode.get("payload");
            String username = payloadNode.get("username").asText();
            String password = payloadNode.get("password").asText();
            String clph_NO = payloadNode.get("clph_NO").asText();
            String dvsn_CD = payloadNode.get("dvsn_CD").asText();
            String dvsn_NM = payloadNode.get("dvsn_NM").asText();
            String jbcl_NM = payloadNode.get("jbcl_NM").asText();
            String eml = payloadNode.get("eml").asText();
            String flnm = payloadNode.get("flnm").asText();
            String prfl_PHTG = payloadNode.get("prfl_PHTG").asText();

            DocumentUsers documentUsers = new DocumentUsers();
            documentUsers.setUsername(username);
            documentUsers.setDept_id(Long.valueOf(dvsn_CD));
            documentUsers.setRole(Role.USER);
            documentUsers.setStatus(1L);
            documentUsers.setPassword(password);
            documentUsers.setImage(prfl_PHTG);

            DocumentUsers existUser = documentUsersRepository.findByUsername(username);

            System.out.println("exist >> " + existUser);

            if(existUser == null){
                documentUsersRepository.save(documentUsers);
            }

            return (usernameFromToken.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Mono<String> getUserDetails(String userId){
        return webClient
                .baseUrl(API_URL)
                .build()
                .get()
                .uri("/api/v1/auth/user-details/" + userId)
                .retrieve()
                .bodyToMono(String.class);
    }


}
