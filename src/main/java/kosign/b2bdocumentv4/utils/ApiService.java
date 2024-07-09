package kosign.b2bdocumentv4.utils;

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
}
