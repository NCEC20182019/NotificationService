package nc.project.NotificationSender;

import nc.project.model.Notification;
import nc.project.model.User;

public interface NotificationSender {
    void send(User user, Notification notification);
}
