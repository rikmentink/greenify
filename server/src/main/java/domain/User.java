package domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
   @GeneratedValue
   @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Intervention> interventions;

    public User(String firstName, String lastName, String email, List<Intervention> interventions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interventions = interventions;
    }

    protected User() {
    }

    public void addIntervention(String name, String description) {
        this.interventions.add(new Intervention(name, description));
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
