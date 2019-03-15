package nc.project.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class NotificationSubscribeDTO {
    private String text;
    private Set<String> emails;

    public NotificationSubscribeDTO(String text, Set<String> emails) {
        this.text = text;
        this.emails = emails;
    }
}
