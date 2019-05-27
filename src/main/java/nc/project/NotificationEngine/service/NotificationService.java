package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.User;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import nc.project.NotificationEngine.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class NotificationService {

  private final SubscriptionRepository subscriptionRepository;
  private final UserService userService;
  private final MessageService messageService;
  private Queue<Subscription> subscriptionsToNotify;
  private static Logger logger = LoggerFactory.getLogger(NotificationService.class);

  @Autowired
  public NotificationService(UserService userService, MessageService messageService, SubscriptionRepository subscriptionRepository) {
    this.userService = userService;
    this.messageService = messageService;
    this.subscriptionRepository = subscriptionRepository;
    this.subscriptionsToNotify = new LinkedList<>();

  }

  public void checkSubscriptions(TriggerDTO triggerData) {
    switch (triggerData.getTriggerFlag()) {
      case CREATE:
        subscriptionsToNotify.addAll(subscriptionRepository
                .findAreaAndTypeSubscriptions(triggerData.getLatitude(), triggerData.getLongitude(), triggerData.getType()));
        break;
      case MODIFY:
        subscriptionsToNotify.addAll(subscriptionRepository
                //TODO org.postgresql.util.PSQLException: ERROR: operator does not exist: character varying = bytea
                //  Подсказка: No operator matches the given name and argument types. You might need to add explicit type casts.
                //  Позиция: 113
                // may be caused by type is null
                .findAreaAndTypeSubscriptions(triggerData.getLatitude(), triggerData.getLongitude(), triggerData.getType()));
      case DELETE:
        subscriptionsToNotify.addAll(subscriptionRepository.findEventSubscription(triggerData.getEventId()));
        break;
    }
    notifyUsers(triggerData);
  }
  private void notifyUsers(TriggerDTO trigger) {
    // На каждую подписку отсылается по 1 письму. Если у пользователя две - получит 2 письма
    while (!subscriptionsToNotify.isEmpty()) {
      Subscription sub = subscriptionsToNotify.poll();
      logger.debug("sub: {}", sub.toString());
      logger.debug("user id: {}", sub.getUserId());
      User user = userService.getUser(sub.getUserId());
      if(user != null){
        userService.getSender(user).send(user, messageService.createMessage(user, sub, trigger));
      }
    }
  }
}
