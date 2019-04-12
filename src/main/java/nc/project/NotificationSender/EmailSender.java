package nc.project.NotificationSender;

import nc.project.model.Message;
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

    public void send(User user, Message message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getText());

        mailSender.send(mailMessage);
    }
}
