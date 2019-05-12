package nc.project.NotificationEngine.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ToggleDTO {
  private int subId;
  @JsonProperty
  private boolean isEnable;
}