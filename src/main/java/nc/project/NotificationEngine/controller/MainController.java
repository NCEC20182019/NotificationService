package nc.project.NotificationEngine.controller;

import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.dto.SubscriptionDTO;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import nc.project.NotificationEngine.service.NotificationService;
import nc.project.NotificationEngine.service.SubscriptionService;
import nc.project.NotificationEngine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(SubscriptionService subscriptionService,
                          NotificationService notificationService) {
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    public String getUser(@PathVariable(value = "id") int id){
        return userService.getUser(id).toString();
    }

    @GetMapping("/subscriptions/{id}")
    public List<Subscription> getSubscriptionsByUserId(@PathVariable int id){
        logger.debug("[getSubscriptionByUserId] input {}", id);
        return subscriptionService.findAll(id);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody SubscriptionDTO newSubscription){
        logger.debug("[subscribe] input {}", newSubscription);
        subscriptionService.subscribeOrUpdate(newSubscription);
    }

    @PostMapping("/subscribe-or-update")
    public void subscribeOrUpdate(@RequestBody List<SubscriptionDTO> subs){
        logger.debug("[subscribeOrUpdate] input {} size {}", subs.getClass().getTypeName(), subs.size());
        subs.forEach(subscriptionService::subscribeOrUpdate);
    }

//    @PostMapping("/unsubscribe")
//    public void unsubscribe(@RequestBody int[] ids){
//        subscriptionService.unsubscribe(ids);
//    }

    @DeleteMapping("/unsubscribe/{id}")
    public void unsubscribe(@PathVariable int id){
        logger.debug("[unsubscribe] input {}", id);
        subscriptionService.unsubscribe(id);
    }

    @DeleteMapping("/unsubscribe/{userId}/{eventId}")
    public void unsubscribe(@PathVariable int userId, @PathVariable int eventId){
        logger.debug("[unsubscribe] inputs: userId {}, eventId {}", userId, eventId);
        subscriptionService.unsubscribe(userId, eventId);
    }

    @PostMapping("/trigger")
    public void trigger(@RequestBody TriggerDTO triggerData){
        logger.debug("[trigger] input {}", triggerData);
        notificationService.checkSubscriptions(triggerData);
    }

    @GetMapping("/check/{userId}/{eventId}")
    public boolean check(@PathVariable int userId, @PathVariable int eventId){
        return subscriptionService.checkSubscription(userId, eventId);
    }
}
