package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;

import java.util.List;

@Getter
public class PersonDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Intervention> interventions;

    public PersonDto(Long id, String firstName, String lastName, String email, List<Intervention> interventions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.interventions = interventions;
    }

    public static PersonDto fromEntity(Person person) {
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getInterventions());
    }
}
