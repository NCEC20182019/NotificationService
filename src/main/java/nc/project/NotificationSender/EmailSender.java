package nc.project.NotificationSender;

import nc.project.NotificationEngine.model.Message;
import nc.project.NotificationEngine.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements NotificationSender{

    private static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public EmailSender(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void send(User user, Message message){
        MimeMessagePreparator mailMessage = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(user.getEmail());
            helper.setFrom("LemmeKnow <" + username + ">");
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), true);
        };

        try {
            mailSender.send(mailMessage);
            logger.debug("Сообщение отправлено {}", message);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
