package nc.project.model;

import lombok.Data;

@Data
public class Notification {
    private int id;
    private Template message;
    private String username;
    private String subscriptionName;

    public Notification(String username, String subsName, Template message)
    {
        this.message = message;
        this.username = username;
        this.subscriptionName = subsName;
    }

    public String getMessage() {
        return String.format(message.getText(), username, subscriptionName);
    }
    public String getMessageSubject(){
        return message.getSubject();
    }
}
