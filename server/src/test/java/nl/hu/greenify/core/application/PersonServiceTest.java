package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Person Service Test")
public class PersonServiceTest {
    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final PersonService personService = new PersonService(personRepository);
    private Person person;
    private Person person2;
    private Person person3;

    @BeforeEach
    void setUp() {
        person = new Person("firstName", "lastName", "firstname@gmail.com");
        person.setId(1L);
        person2 = new Person("firstName2", "lastName2", "firstname@gmail.com");
        person3 = new Person("firstName3", "lastName3", "person3@gmai.com");
        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findByEmail(person.getEmail())).thenReturn(Optional.of(person));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
    }

    @DisplayName("Creating a person should be possible")
    @Test
    void createPerson() {
        assertDoesNotThrow(() -> personService.createPerson(person3));
    }

    @DisplayName("Creating a person with an existing email should throw an exception")
    @Test
    void createPersonShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> personService.createPerson(person));
    }

    @DisplayName("Getting a person by email should be possible")
    @Test
    void getPersonByEmail() {
        assertDoesNotThrow(() -> personService.getPersonByEmail(person.getEmail()));
    }

    @DisplayName("Getting a person by email that does not exist should throw an exception")
    @Test
    void getPersonByEmailShouldThrowException() {
        assertThrows(PersonNotFoundException.class, () -> personService.getPersonByEmail("fake-email@gmail.com"));
    }

    @DisplayName("Getting a person by id should be possible")
    @Test
    void getPersonById() {
        assertDoesNotThrow(() -> personService.getPersonById(person.getId()));
    }

    @DisplayName("Getting a person by id that does not exist should throw an exception")
    @Test
    void getPersonByIdShouldThrowException() {
        assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(3L));
    }

    @DisplayName("Getting all persons should be possible")
    @Test
    void getAllPersons() {
        assertDoesNotThrow(personService::getAllPersons);
    }
}
