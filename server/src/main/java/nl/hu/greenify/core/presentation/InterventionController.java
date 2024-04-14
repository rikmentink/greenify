package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.presentation.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intervention")
public class InterventionController {
    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addIntervention(@RequestParam Long id, String name, String description) {
        try {
            Person person = this.interventionService.getPersonById(id);
            this.interventionService.addIntervention(name, description, person);
            return ResponseEntity.ok(PersonDto.fromEntity(person));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/phase/{personId}")
    public ResponseEntity<?> addPhaseToIntervention(@RequestParam Long personId, String name, PhaseName phaseName) {
        try {

            Person person = this.interventionService.getPersonById(personId);
            this.interventionService.addPhaseToIntervention(name, phaseName, person);

            return ResponseEntity.ok(PersonDto.fromEntity(person));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@RequestParam Long id) {
        try {
            PersonDto personDto = PersonDto.fromEntity(this.interventionService.getPersonById(id));
            return ResponseEntity.ok(personDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

