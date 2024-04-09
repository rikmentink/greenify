package domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    public User(String firstName, String lastName, String email) {
        if(firstName == null || lastName == null || email == null) {
            throw new IllegalArgumentException("User should have a first name, last name and email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interventions = new ArrayList<>();
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
