package nc.project.NotificationEngine.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Map;

@Service
public class TokenService {

    @Value("${nc.project.security.url}")
    private String serviceUrl;

//    @Value("${nc.project.security.client-id}")
//    private String clientId;
//
//    @Value("${nc.project.security.client-secret}")
//    private String clientSecret;

    private static final String ACCESS_TOKEN = "access_token";

    String getToken(String username, String password, String grant_type) throws IllegalAccessException{

//        HashMap<String, String> requestBody = new HashMap<>();
//
//        requestBody.put("grant_type", grant_type);
//        requestBody.put("username", username);
//        requestBody.put("password", password);

        String URI = serviceUrl + "/oauth/token" + "?" +
                String.format("%s=%s&%s=%s&%s=%s", "grant_type", grant_type, "username", username, "password", password);
//        String CONTENT_TYPE = "application/x-www-form-urlencoded";
        // TODO clientId and clientSecret !!!
        String ENCODING = "Basic " +
                Base64.getEncoder().encodeToString(String.format("%s:%s", "clientId", "clientSecret").getBytes());

//        AtomicReference<String> token = null;
//        authorization.subscribe(map -> {
//            token.set((String) map.get(ACCESS_TOKEN));
//        });

        String token = null;
        try {
             token =  (String)WebClient.create().post()
                     .uri(URI)
                     .accept(MediaType.APPLICATION_FORM_URLENCODED)
                     .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                     .header("Authorization", ENCODING)
                     .retrieve()
                     .bodyToMono(Map.class)
                     .block().get(ACCESS_TOKEN);
        } catch (Exception e){
            throw new IllegalAccessException("Can't reach access token");
        }

        return token;
    }
}
