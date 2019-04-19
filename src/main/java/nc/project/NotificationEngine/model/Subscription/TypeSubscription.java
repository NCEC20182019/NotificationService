package nc.project.NotificationEngine.model.Subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@DiscriminatorValue(value = "type")
public class TypeSubscription extends Subscription {
    private String type;
}
