package nc.project.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class NotificationGetDTO {
    private int id;
    private String text;
    private Set<String> emails;

    public NotificationGetDTO(int id, String text, Set<String> emails) {
        this.id = id;
        this.text = text;
        this.emails = emails;
    }
}
