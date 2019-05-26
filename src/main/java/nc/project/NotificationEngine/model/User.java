package nc.project.NotificationEngine.model;

import lombok.Data;
import nc.project.NotificationEngine.model.enums.PreferredNotificationChannel;

@Data
public class User {
    private int id;
    private String username;
    private PreferredNotificationChannel notificationChannel;
    private String email;
}

