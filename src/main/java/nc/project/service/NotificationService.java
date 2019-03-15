package nc.project.service;

import com.sun.tools.classfile.Opcode;
import nc.project.model.Notification;
import nc.project.model.User;
import nc.project.model.dto.NotificationSubscribeDTO;
import nc.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MailService mailService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               MailService mailService) {
        this.notificationRepository = notificationRepository;
        this.mailService = mailService;
    }

    public void subscribe(NotificationSubscribeDTO newNotification){
        Set<User> users = new HashSet<>();
        newNotification.getEmails().forEach(x -> {
            users.add(new User(x));
        });
        Notification notification = new Notification(
                newNotification.getText(),
                users
        );
        notificationRepository.save(notification);
    }

    public void startNotify() {
        notificationRepository.findAll()
                .forEach(n -> n.getUsers()
                    .forEach(u -> mailService.send(u.getEmail(), "Notification", n.getText())
                    )
                );

    }
}
