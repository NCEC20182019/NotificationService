package nc.project.NotificationSender;

import nc.project.model.Notification;
import nc.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements NotificationSender{

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public EmailSender(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void send(User user, Notification notification){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        //TODO тема должна генериться в зависимости от типа нотификации (создание, изменени, наступление события и тд)
        // и хранится в шаблоне
        mailMessage.setSubject(notification.getMessageSubject());
        mailMessage.setText(notification.getMessage());

        mailSender.send(mailMessage);
    }
}
