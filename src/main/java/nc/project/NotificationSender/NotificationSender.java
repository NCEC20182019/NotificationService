package nc.project.NotificationSender;

import nc.project.model.Message;
import nc.project.model.User;

public interface NotificationSender {
    void send(User user, Message message);
}
