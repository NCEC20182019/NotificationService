package nc.project.NotificationEngine.service;

import com.fasterxml.classmate.GenericType;
import nc.project.NotificationSender.EmailSender;
import nc.project.NotificationSender.NotificationSender;
import nc.project.NotificationSender.PopupSender;
import nc.project.NotificationEngine.model.User;
import nc.project.NotificationEngine.model.enums.PreferredNotificationChannel;
import net.minidev.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Value("${nc.project.security.url}")
    private String serviceUrl;

//    @Value("${nc.project.security.client-id}")
//    private String clientId;
//
//    @Value("${nc.project.security.client-secret}")
//    private String clientSecret;

    private final EmailSender emailSender;
    private final PopupSender popupSender;
    private final TokenService tokenService;

    @Autowired
    public UserService(EmailSender emailSender, PopupSender popupSender, TokenService tokenService) {
        this.emailSender = emailSender;
        this.popupSender = popupSender;
        this.tokenService = tokenService;
    }

    public Mono<User> getUser(int id){
//        User user = new User();
//        user.setId(id);
//        user.setName("Artem");
//        user.setEmail("mail@mail.ru");
//        user.setNotificationChannel(PreferredNotificationChannel.EMAIL);

//        return Mono.just(user);

//        String token;
//        //Внизу креды админа
//        try {
//            token = tokenService.getToken("dominiqewilkins@gmail.com", "Yfevtyrj1","password");
//        } catch (IllegalAccessException e) {
//            System.out.println(e.toString());
//            return null;
//        }
//
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        String token = "";
        if (details instanceof OAuth2AuthenticationDetails){
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)details;
            token = oAuth2AuthenticationDetails.getTokenValue();
        }

        final String ftoken = token;

        return WebClient.create().get()
                .uri(serviceUrl + "/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
                        .clientRegistrationId("notif"))
                .headers(h -> h.setBearerAuth(ftoken))
                .retrieve()
                .bodyToMono(User.class);
    }

    public Flux<User> getUsers(List<Integer> ids){
        return WebClient.create().post()
                .uri(serviceUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(ids))
                .retrieve()
                .bodyToFlux(User.class);
    }

    public NotificationSender getSender(User u){
        //NotificationSender sender = null;
        switch(u.getNotificationChannel()){
            case EMAIL:{
                return emailSender;
            }
            case POPUP:{
                return popupSender;
            }
//            case SMS:{
//                return new SmsSender();
//                break;
//            }
            default:{
                return emailSender;
            }
        }
    }
}
