package nc.project.service;

import com.fasterxml.classmate.GenericType;
import nc.project.NotificationSender.EmailSender;
import nc.project.NotificationSender.NotificationSender;
import nc.project.NotificationSender.PopupSender;
import nc.project.model.User;
import nc.project.model.enums.PreferredNotificationChannel;
import net.minidev.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

    @Value("${nc.project.security.client-id}")
    private String clientId;

    @Value("${nc.project.security.client-secret}")
    private String clientSecret;

    @Autowired
    TokenService tokenService;

    private final EmailSender emailSender;
    private final PopupSender popupSender;

    @Autowired
    public UserService(EmailSender emailSender, PopupSender popupSender) {
        this.emailSender = emailSender;
        this.popupSender = popupSender;
    }

    public Mono<User> getUser(int id){
//        User user = new User();
//        user.setId(id);
//        user.setName("Artem");
//        user.setEmail("mail@mail.ru");
//        user.setNotificationChannel(PreferredNotificationChannel.EMAIL);

//        return Mono.just(user);

        String token;
        //Внизу креды админа
        try {
            token = tokenService.getToken("dominiqewilkins@gmail.com", "Yfevtyrj1","password");
        } catch (IllegalAccessException e) {
            System.out.println(e.toString());
            return null;
        }

        Mono<User> user = WebClient.create().get()
                .uri(serviceUrl + "/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class);

        User tmp = user.block();

        return user;
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
