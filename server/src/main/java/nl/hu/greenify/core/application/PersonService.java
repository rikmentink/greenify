package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public Person getPersonByEmail(String email) {
        System.out.println("Fetching person by email: " + email);
        Optional<Person> person = personRepository.findByEmail(email);
        System.out.println("Fetched person: " + person);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with email " + email + " does not exist");
        } else {
            return person.get();
        }
    }


    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
}
