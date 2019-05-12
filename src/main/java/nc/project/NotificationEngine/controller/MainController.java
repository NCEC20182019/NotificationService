package nc.project.NotificationEngine.controller;

import nc.project.NotificationEngine.model.Subscription.AreaSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.dto.SubscriptionDTO;
import nc.project.NotificationEngine.model.dto.ToggleDTO;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import nc.project.NotificationEngine.repository.SubscriptionRepository;
import nc.project.NotificationEngine.service.NotificationService;
import nc.project.NotificationEngine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final ModelMapper modelMapper = new ModelMapper();
    private final SubscriptionRepository subscriptionRepo;
    private final NotificationService notificationService;

    @Autowired
    public MainController(SubscriptionRepository subscriptionRepo,
                          NotificationService notificationService) {
        this.subscriptionRepo = subscriptionRepo;
        this.notificationService = notificationService;
        /*modelMapper.addMappings(new PropertyMap<Message, NotificationGetDTO>() {
            @Override
            protected void configure() {
                Set<String> emails = new HashSet<>();
                source.getUsers().forEach(u -> emails.add(u.getEmail()));
                map().setEmails(emails);
            }
        });*/
    }

    @GetMapping("/subscriptions")
    public List<Subscription> getAllSubscriptions(){
        List<Subscription> response = new ArrayList<>();
        subscriptionRepo.findAll().forEach(response::add);
        return response;
    }

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    public String getUser(@PathVariable(value = "id") int id){
        return userService.getUser(id).block().toString();
    }



    @PostMapping("/subscribe")
    public void subscribe(@RequestBody SubscriptionDTO newSubscription){
        notificationService.subscribe(newSubscription);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody int[] ids){
        notificationService.unsubscribe(ids);
    }

    @PostMapping(value = "/toggle")
    public void toggleSubscriptions(@RequestBody List<ToggleDTO> toggles){
        notificationService.toggleSubscription(toggles);
    }

    @PostMapping(value = "/update/areas")
    public void updateAreas(@RequestBody List<AreaSubscription> newAreaSubs) {
        notificationService.updateAreaSubscriptions(newAreaSubs);
    }

    @PostMapping("/trigger")
    public void trigger(@RequestBody TriggerDTO triggerData){
        notificationService.checkSubscriptions(triggerData);
    }
}
