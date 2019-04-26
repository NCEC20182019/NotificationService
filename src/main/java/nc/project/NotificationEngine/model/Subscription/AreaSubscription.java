package nc.project.NotificationEngine.model.Subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@DiscriminatorValue(value = "area")
public class AreaSubscription extends Subscription {
    private double latitude;
    private double longitude;
    private double radius;


}
