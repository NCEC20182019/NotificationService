package nc.project.NotificationEngine.model.Subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@DiscriminatorValue(value = "event")
public class EventSubscription extends Subscription {
    @Column(name="event_id")
    private int eventId;
}
