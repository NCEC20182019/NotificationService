package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.User;
import nc.project.NotificationSender.EmailSender;
import nc.project.NotificationSender.NotificationSender;
import nc.project.NotificationSender.PushSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final PushSender pushSender;
    private final TokenService tokenService;

    @Autowired
    public UserService(EmailSender emailSender, PushSender pushSender, TokenService tokenService) {
        this.emailSender = emailSender;
        this.pushSender = pushSender;
        this.tokenService = tokenService;
    }

    public User getUser(int id){
//        User user = new User();
//        user.setId(id);
//        user.setUsername("Artem");
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
                //.attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction
                //        .clientRegistrationId("notif"))
                .headers(h -> h.setBearerAuth(ftoken))
                .retrieve()
                .bodyToMono(User.class).block();
    }

    public NotificationSender getSender(User u){
        //NotificationSender sender = null;
        //TODO fix NPE
        switch(u.getNotificationChannel()){
            case EMAIL:{
                return emailSender;
            }
            case PUSH:{
                return pushSender;
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
