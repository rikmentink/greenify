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
        validateInput(firstName, "First name");
        validateInput(lastName, "Last name");
        validateEmail(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interventions = new ArrayList<>();
    }

    public void addIntervention(String name, String description) {
        for(Intervention intervention : this.interventions) {
            if(intervention.getName().equals(name) && intervention.getDescription().equals(description)) {
                throw new IllegalArgumentException("Intervention with name " + name + "&" + description + "already exists");
            }
        }
        this.interventions.add(new Intervention(name, description));
    }

    private void validateInput(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
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
