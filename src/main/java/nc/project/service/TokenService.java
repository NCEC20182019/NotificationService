package nc.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TokenService {

    @Value("${nc.project.security.url}")
    private String serviceUrl;

    @Value("${nc.project.security.client-id}")
    private String clientId;

    @Value("${nc.project.security.client-secret}")
    private String clientSecret;

    private static final String ACCESS_TOKEN = "access_token";

    String getToken(String username, String password, String grant_type){

        HashMap<String, String> requestBody = new HashMap<>();

        requestBody.put("grant_type", grant_type);
        requestBody.put("username", username);
        requestBody.put("password", password);


        Mono<Map> authorization = WebClient.create().post()
                .uri(serviceUrl + "/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()))
                .header("Content-type", "application/json")
                .body(Mono.just(requestBody), HashMap.class)
                .retrieve()
                .bodyToMono(Map.class);



//        AtomicReference<String> token = null;
//        authorization.subscribe(map -> {
//            token.set((String) map.get(ACCESS_TOKEN));
//        });


        String token = (String)authorization.block().get(ACCESS_TOKEN);

        return token;
    }
}
