package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Person;

import java.util.Optional;

public class PersonService {

    public Person getPersonById(Long id) {
        Optional<Person> person = greenifyRepository.findById(id);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with id " + id + " does not exist");
        } else {
            return person.get();
        }
    }

    // TODO: Move to separate PersonService
    public Person getPersonByUsername(String username) {
        Optional<Person> person = greenifyRepository.findByUsername(username);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with username " + username + " does not exist");
        } else {
            return person.get();
        }
    }
}
