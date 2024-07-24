package kosign.b2bdocumentv4.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosign.b2bdocumentv4.payload.doc_users.UserDetiailPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ApiService {
    private final WebClient.Builder webClient;
    @Value("${API_URL}")
    private String API_URL;

    public ApiService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getUserDetails(String userId, String userInttId) {
        return WebClient
                .create(API_URL)
                .get()
                .uri("/api/v1/auth/user-details/" + userId + "/" + userInttId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                return Mono.error(new IllegalArgumentException(errorBody));
                            });
                })
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    return Mono.error(new RuntimeException("WebClient error: " + ex.getResponseBodyAsString(), ex));
                })
                .onErrorResume(ex -> {
                    return Mono.error(new RuntimeException("Unexpected error: " + ex.getMessage(), ex));
                });
    }


    public UserDetiailPayload getUserDetailFromBizLogin(String userId, String company) throws JsonProcessingException {
        String json = getUserDetails(userId, company).block();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode payloadNode = jsonNode.get("payload");
        String username = payloadNode.get("username").asText();
        String clph_NO = payloadNode.get("clph_NO").asText();
        String dvsn_CD = payloadNode.get("dvsn_CD").asText();
        String dvsn_NM = payloadNode.get("dvsn_NM").asText();
        String jbcl_NM = payloadNode.get("jbcl_NM").asText();
        String eml = payloadNode.get("eml").asText();
        String use_INTT_ID = payloadNode.get("use_INTT_ID").asText();
        String flnm = payloadNode.get("flnm").asText();
        String prfl_PHTG = payloadNode.get("prfl_PHTG").asText();

        UserDetiailPayload paylaod = new UserDetiailPayload();
        paylaod.setDepartment(dvsn_NM);
        paylaod.setDepartmentId(dvsn_CD);
        paylaod.setPosition(jbcl_NM);
        paylaod.setPhoneNumber(clph_NO);
        paylaod.setUsername(username);
        paylaod.setEmail(eml);
        paylaod.setCompany(use_INTT_ID);
        paylaod.setFullName(flnm);
        paylaod.setProfile(prfl_PHTG);

        return paylaod;
    }
}
