package nc.project.controller;

import nc.project.model.Subscription.Subscription;
import nc.project.model.dto.SubscriptionDTO;
import nc.project.model.dto.TriggerDTO;
import nc.project.repository.SubscriptionRepository;
import nc.project.service.NotificationService;
import nc.project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
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

    @PostMapping("/trigger")
    public void trigger(@RequestBody TriggerDTO triggerData){
        notificationService.checkSubscriptions(triggerData);
    }
}
