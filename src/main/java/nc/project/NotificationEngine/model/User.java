package nc.project.NotificationEngine.model;

import lombok.*;
import nc.project.NotificationEngine.model.enums.PreferredNotificationChannel;

@Data
public class User {
    private int id;
    private String name;
    private PreferredNotificationChannel notificationChannel;
    private String email;
}

