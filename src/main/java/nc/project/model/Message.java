package nc.project.model;

import lombok.Data;

@Data
public class Message {
    private String subject;
    private String text;
    private String subscriptionName;

    public Message(String subject, String text, String subscriptionName) {
        this.subject = subject;
        this.text = text;
        this.subscriptionName = subscriptionName;
    }
}
