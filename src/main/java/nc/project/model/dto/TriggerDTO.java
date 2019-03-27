package nc.project.model.dto;

import lombok.Data;
import nc.project.model.enums.TriggerFlags;

@Data
public class TriggerDTO {
    private int eventId;
    private TriggerFlags triggerFlag;
    private String type;
    private double latitude;
    private double longitude;
}
