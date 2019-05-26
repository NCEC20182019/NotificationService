package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.Message;
import nc.project.NotificationEngine.model.Subscription.EventSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.User;
import nc.project.NotificationEngine.model.dto.TriggerDTO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class MessageService {

    private final VelocityEngine velocityEngine;
    @Autowired
    public MessageService(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public Message createMessage(User user, Subscription sub, TriggerDTO trigger){
        VelocityContext context = new VelocityContext();
        context.put("username", user.getUsername());
        StringWriter stringWriter = new StringWriter();

        switch (trigger.getTriggerFlag()) {
            case CREATE:
                context.put("content", "Появилось новое событие! Посмотри, возможно тебе это будет интересно:");
                context.put("toEvent", trigger.getEventId());
                velocityEngine.mergeTemplate("velocity.templates/CreateOrModifyNotification.vm",
                        "UTF-8", context, stringWriter);
                break;
            case MODIFY:
                if (user.getId() == sub.getUserId() && ((EventSubscription)sub).getEventId() == trigger.getEventId()) {
                    context.put("content", "Что-то изменилось! Нужно проверить:");
                } else {
                    context.put("content", "Появилось новое событие! Посмотри, возможно тебе это будет интересно:");
                }
                context.put("toEvent", trigger.getEventId());
                velocityEngine.mergeTemplate("velocity.templates/CreateOrModifyNotification.vm",
                        "UTF-8", context, stringWriter);
                break;
            case DELETE:
                velocityEngine.mergeTemplate("velocity.templates/DeleteNotification.vm",
                        "UTF-8", context, stringWriter);
                break;
        }

        String text = stringWriter.toString();

        return new Message("Notification from lemmeknow.tk", text, sub.getName());
    }
}
