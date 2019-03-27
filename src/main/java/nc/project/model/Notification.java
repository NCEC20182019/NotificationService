package nc.project.model;

import lombok.Data;

@Data
public class Notification {
    private int id;
    private Template message;

    public Notification(String username, String subsName)
    {
        message.setUsername(username);
        message.setSubscriptionName(subsName);
    }

    public String getMessage() {
        return message.toString();
    }
    public String getMessageSubject(){
        return message.getSubject();
    }
}
