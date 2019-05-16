package nc.project.NotificationEngine.controller;

import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.dto.SubscriptionDTO;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import nc.project.NotificationEngine.service.NotificationService;
import nc.project.NotificationEngine.service.SubscriptionService;
import nc.project.NotificationEngine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final ModelMapper modelMapper = new ModelMapper();
    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

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
        return userService.getUser(id).block().toString();
    }

    @GetMapping("/subscriptions/{id}")
    public List<Subscription> getSubscriptionsByUserId(@PathVariable int id){
        return subscriptionService.findAll(id);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody SubscriptionDTO newSubscription){
        subscriptionService.subscribeOrUpdate(newSubscription);
    }

    @PostMapping("/subscribe-or-update")
    public void subscribeOrUpdate(@RequestBody List<SubscriptionDTO> subs){
        subs.forEach(subscriptionService::subscribeOrUpdate);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody int[] ids){
        subscriptionService.unsubscribe(ids);
    }

    @DeleteMapping("/unsubscribe/{id}")
    public void unsubscribe(@PathVariable int id){
        subscriptionService.unsubscribe(id);
    }
    @PostMapping("/trigger")
    public void trigger(@RequestBody TriggerDTO triggerData){
        notificationService.checkSubscriptions(triggerData);
    }
}
