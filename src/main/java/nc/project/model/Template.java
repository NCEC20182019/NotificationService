package nc.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private String subject;
    @Transient
    private String username;
    @Transient
    private String subscriptionName;

    @Override
    public String toString() {
        return String.format(text, username, subscriptionName);
    }
}
