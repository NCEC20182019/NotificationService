package nc.project.service;

import nc.project.NotificationSender.NotificationSender;
import nc.project.model.Message;
import nc.project.model.Subscription.AreaSubscription;
import nc.project.model.Subscription.EventSubscription;
import nc.project.model.Subscription.Subscription;
import nc.project.model.Subscription.TypeSubscription;
import nc.project.model.User;
import nc.project.model.dto.SubscriptionDTO;
import nc.project.model.dto.TriggerDTO;
import nc.project.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class NotificationService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final MessageService messageService;
    private NotificationSender notificationSender = null;
    private Queue<Subscription> subscriptionsToNotify;

    @Autowired
    public NotificationService(SubscriptionRepository subscriptionRepository, UserService userService, MessageService messageService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.subscriptionsToNotify = new PriorityQueue<>();
    }

    public void subscribe(SubscriptionDTO newSubscription) {
        //TODO radius нужно умножать на 0.0143051 примерно
        Subscription sub;
        if (newSubscription.checkArea()) {
            sub = new AreaSubscription();
            sub.setName(newSubscription.getName());
            sub.setUserId(newSubscription.getUserId());
            sub.setEnabled(true);
            //TODO возможно нужно приведение типа присвоить новой переменной потом сохранять
            ((AreaSubscription) sub).setLatitude(newSubscription.getLatitude());
            ((AreaSubscription) sub).setLongitude(newSubscription.getLongitude());
            ((AreaSubscription) sub).setRadius(newSubscription.getRadius());
            subscriptionRepository.save(sub);
        }
        if (newSubscription.getType() != null) {
            sub = new TypeSubscription();
            sub.setName(newSubscription.getName());
            sub.setUserId(newSubscription.getUserId());
            sub.setEnabled(true);
            ((TypeSubscription) sub).setType(newSubscription.getType());
            subscriptionRepository.save(sub);
        }
        if (newSubscription.getEventId() != 0) {
            sub = new EventSubscription();
            sub.setName(newSubscription.getName());
            sub.setUserId(newSubscription.getUserId());
            sub.setEnabled(true);
            ((EventSubscription) sub).setEventId(newSubscription.getEventId());
            subscriptionRepository.save(sub);
        }


    }

    public void checkSubscriptions(TriggerDTO triggerData) {
        switch (triggerData.getTriggerFlag()) {
            case CREATE:
                subscriptionsToNotify.addAll(subscriptionRepository
                        .findAreaAndTypeSubscriptions(triggerData.getLatitude(), triggerData.getLongitude(), triggerData.getType()));
                break;
            case MODIFY:
            case DELETE:
                subscriptionsToNotify.addAll(subscriptionRepository.findEventSubscription(triggerData.getEventId()));
                break;
        }
        notifyUsers();
    }

    private void notifyUsers() {
        while (!subscriptionsToNotify.isEmpty()) {
            Subscription sub = subscriptionsToNotify.poll();
            User user = userService.getUser(sub.getUserId()).block();
            if(user != null){
                notificationSender = userService.getSender(user);
                notificationSender.send(user, messageService.createMessage(user.getName(), sub.getName()));
            }
        }
    }
}
