package nl.hu.greenify.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.greenify.domain.enums.PhaseName;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    private List<Intervention> interventions;

    protected Person() {
    }

    public Person(String firstName, String lastName, String email) {
        validateInput(firstName, "First name");
        validateInput(lastName, "Last name");
        validateEmail(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interventions = new ArrayList<>();
    }

    public void addPhaseToIntervention(String interventionName, PhaseName phaseName) { //User side
        boolean interventionFound = false;
        for (Intervention i : this.interventions) {
            if (i.getName().equals(interventionName)) {
                i.addPhase(phaseName);
                interventionFound = true;
                break;
            }
        }
        if (!interventionFound) {
            throw new NoSuchElementException("Intervention with name " + interventionName + " does not exist");
        }
    }

    public void addIntervention(String name, String description) {
        for(Intervention intervention : this.interventions) {
            if(intervention.getName().equals(name) && intervention.getDescription().equals(description)) {
                throw new IllegalArgumentException("Intervention with name " + name + " & " + description + " already exists");
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
}