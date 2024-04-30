package kosign.b2bdocumentv4.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {
    private final WebClient.Builder webClient;
    @Value("${API_URL}")
    private String API_URL;

    public ApiService(WebClient.Builder webClient) {
        this.webClient = webClient;
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
