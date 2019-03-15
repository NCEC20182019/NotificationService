package nc.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude="users")

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private int id;
    //TODO rename text to content
    @Column(name="notification_text")
    private String text;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_notification",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Notification(){}

    /*public Notification(String text, User... users) {
        this.text = text;
        this.users = Stream.of(users).collect(Collectors.toSet());
        this.users.forEach(x->x.getNotifications().add(this));
    }*/
    public Notification(String text, Set<User> users){
        this.text = text;
        this.users = users;
        this.users.forEach(u->u.getNotifications().add(this));
    }
}
