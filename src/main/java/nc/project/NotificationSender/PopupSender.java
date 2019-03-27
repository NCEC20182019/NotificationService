package nc.project.NotificationSender;

import nc.project.model.Notification;
import nc.project.model.User;
import org.springframework.stereotype.Service;

@Service
public class PopupSender implements NotificationSender {
    @Override
    public void send(User user, Notification notification) {

    }
}
