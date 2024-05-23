package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController extends Controller {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getPersonById(@PathVariable("id") Long id) {
        PersonDto personDto = PersonDto.fromEntity(this.personService.getPersonById(id));
        return this.createResponse(personDto);
    }

    @GetMapping(value="/email/{email}", produces="application/json")
    public ResponseEntity<?> getPersonByEmail(@PathVariable String email) {
        PersonDto personDto = PersonDto.fromEntity(this.personService.getPersonByEmail(email));
        System.out.println("Fetched person's email: " + personDto.getEmail());
        return this.createResponse(personDto);
    }

    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
        PersonDto createdPerson = PersonDto.fromEntity(this.personService.createPerson(PersonDto.toEntity(personDto)));
        return this.createResponse(createdPerson);
    }

    @GetMapping(value="/all", produces="application/json")
    public ResponseEntity<?> getAllPersons() {
        return this.createResponse(PersonDto.fromEntities(this.personService.getAllPersons()));
    }

}