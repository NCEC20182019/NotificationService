package nc.project.NotificationEngine.model.dto;

import lombok.Data;
import nc.project.NotificationEngine.model.enums.TriggerFlag;

@Data
public class TriggerDTO {
    private int eventId;
    private TriggerFlag triggerFlag;
    private String type;
    private double latitude;
    private double longitude;
}
