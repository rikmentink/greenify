package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intervention")
public class InterventionController {
    private final InterventionService interventionService;
    private final PersonService personService;

    public InterventionController(InterventionService interventionService, PersonService personService) {
        this.interventionService = interventionService;
        this.personService = personService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createIntervention(@RequestParam String name, String description, Long adminId) {
        try {
            Person admin = this.personService.getPersonById(adminId);
            this.interventionService.createIntervention(name, description, admin);

            return ResponseEntity.ok(PersonDto.fromEntity(admin));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/phase/{personId}")
    public ResponseEntity<?> addPhase(@RequestParam Long personId, String name, PhaseName phaseName) {
        try {

            Person person = this.interventionService.getPersonById(personId);
            this.interventionService.addPhase(name, phaseName, person);

            return ResponseEntity.ok(PersonDto.fromEntity(person));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

