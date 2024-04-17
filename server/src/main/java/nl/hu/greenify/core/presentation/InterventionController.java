package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.domain.Intervention;
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
            this.interventionService.createIntervention(name, description, adminId);

            return ResponseEntity.ok(PersonDto.fromEntity(admin));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/phase/{id}")
    public ResponseEntity<?> addPhase(@RequestParam Long id, PhaseName phaseName) {
        try {
            this.interventionService.addPhase(id, phaseName);

            return ResponseEntity.ok("Phase added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

