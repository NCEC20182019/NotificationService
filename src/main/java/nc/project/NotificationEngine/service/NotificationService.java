package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.User;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import nc.project.NotificationEngine.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class NotificationService {

  private final SubscriptionRepository subscriptionRepository;
  private final UserService userService;
  private final MessageService messageService;
  private Queue<Subscription> subscriptionsToNotify;

  @Autowired
  public NotificationService(UserService userService, MessageService messageService, SubscriptionRepository subscriptionRepository) {
    this.userService = userService;
    this.messageService = messageService;
    this.subscriptionRepository = subscriptionRepository;
    this.subscriptionsToNotify = new PriorityQueue<>();

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
        userService.getSender(user).send(user, messageService.createMessage(user.getName(), sub.getName()));
      }
    }
  }
}
