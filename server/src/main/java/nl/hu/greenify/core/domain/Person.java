package nl.hu.greenify.core.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
public class Person {
    @GeneratedValue
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany
    private List<Survey> surveys;

    protected Person() {
    }

    public Person(String firstName, String lastName, String email) {
        validateInput(firstName, "First name");
        validateInput(lastName, "Last name");
        validateEmail(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
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
}