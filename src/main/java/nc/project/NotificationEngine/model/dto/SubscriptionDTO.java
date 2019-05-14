package nc.project.NotificationEngine.model.dto;

import lombok.Data;
import nc.project.NotificationEngine.model.enums.SubType;

@Data
public class SubscriptionDTO {
    private int id;
    private int userId;
    private String name;
    private SubType subType;
    private double latitude;
    private double longitude;
    private double radius;
    private String type;
    private int eventId;
    private boolean enabled;

    public boolean checkArea() {
        return latitude != 0 && longitude != 0 && radius != 0;
    }
}
