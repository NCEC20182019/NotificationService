package nc.project.service;

import nc.project.NotificationSender.EmailSender;
import nc.project.NotificationSender.NotificationSender;
import nc.project.NotificationSender.PopupSender;
import nc.project.model.User;
import nc.project.model.enums.PreferredNotificationChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {
    @Value("${nc.project.security.url}")
    private String serviceUrl;

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

        return WebClient.create().get()
                .uri(serviceUrl + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
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
