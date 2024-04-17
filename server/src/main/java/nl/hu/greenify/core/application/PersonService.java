package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;

import java.util.Optional;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with id " + id + " does not exist");
        } else {
            return person.get();
        }
    }

    public Person getPersonByUsername(String username) {
        Optional<Person> person = personRepository.findByUsername(username);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with username " + username + " does not exist");
        } else {
            return person.get();
        }
    }
}
