package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.data.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    private final PersonService personService;
    @Autowired
    private final PersonRepository personRepository;

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }
}
