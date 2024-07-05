package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController extends Controller {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getPersonById(@PathVariable("id") Long id) {
        PersonDto personDto = PersonDto.fromEntity(this.personService.getPersonById(id));
        return this.createResponse(personDto);
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/email/{email}", produces="application/json")
    public ResponseEntity<?> getPersonByEmail(@PathVariable String email) {
        PersonDto personDto = PersonDto.fromEntity(this.personService.getPersonByEmail(email));
        return this.createResponse(personDto);
    }

    @Secured("ROLE_VUMEDEWERKER")
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
        PersonDto createdPerson = PersonDto.fromEntity(this.personService.createPerson(PersonDto.toEntity(personDto)));
        return this.createResponse(createdPerson);
    }

    @Secured("ROLE_VUMEDEWERKER")
    @GetMapping(value="/all", produces="application/json")
    public ResponseEntity<?> getAllPersons() {
        return this.createResponse(PersonDto.fromEntities(this.personService.getAllPersons()));
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/current", produces="application/json")
    public ResponseEntity<?> getCurrentPerson() {
        return this.createResponse(PersonDto.fromEntity(this.personService.getCurrentPerson()));
    }

}