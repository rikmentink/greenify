package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getPersonByEmail(@PathVariable("email") String email) {
        PersonDto personDto = PersonDto.fromEntity(this.personService.getPersonByEmail(email));
        return this.createResponse(personDto);
    }

}