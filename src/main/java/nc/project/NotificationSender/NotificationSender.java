package nc.project.NotificationSender;

import nc.project.NotificationEngine.model.Message;
import nc.project.NotificationEngine.model.User;

public interface NotificationSender {
    void send(User user, Message message);
}
