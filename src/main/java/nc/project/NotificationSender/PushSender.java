package nc.project.NotificationSender;

import nc.project.NotificationEngine.model.Message;
import nc.project.NotificationEngine.model.User;
import org.springframework.stereotype.Service;

@Service
public class PushSender implements NotificationSender {
    @Override
    public void send(User user, Message message) {

    }
}
