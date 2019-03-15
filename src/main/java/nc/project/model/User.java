package nc.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    private String email;

    @ManyToMany(mappedBy = "users")
    private Set<Notification> notifications = new HashSet<>();

    public User(){}
    public User(String email) {
        this.email = email;
    }
}

