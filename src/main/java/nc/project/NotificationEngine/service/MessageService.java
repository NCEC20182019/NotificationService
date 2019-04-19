package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.Message;
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

    public Message createMessage(String userName, String subName){
        VelocityContext context = new VelocityContext();
        context.put("username", userName);
        context.put("subName", subName);
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("velocity.templates/Notification.vm",
                "UTF-8", context, stringWriter);
        String text = stringWriter.toString();

        return new Message("Notification from lemmeknow.tk", text, subName);
    }
}
