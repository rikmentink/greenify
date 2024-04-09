package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
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


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
