package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.security.application.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final AccountService accountService;

    public PersonService(PersonRepository personRepository, AccountService accountService) {
        this.personRepository = personRepository;
        this.accountService = accountService;
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " does not exist"));
    }

    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new PersonNotFoundException("Person with email " + email.toLowerCase() + " does not exist"));
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        if(personRepository.findByEmail(person.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Person with email " + person.getEmail() + " already exists");
        }

        return personRepository.save(person);
    }

    public Person getCurrentPerson() {
        return accountService.getCurrentAccount().getPerson();
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}
