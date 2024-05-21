package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Person;

@Getter
public class PersonDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public PersonDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static PersonDto fromEntity(Person person) {
        return new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getEmail());
    }

    public static Person toEntity(PersonDto personDto) {
        return new Person(personDto.getFirstName(), personDto.getLastName(), personDto.getEmail());
    }
}
