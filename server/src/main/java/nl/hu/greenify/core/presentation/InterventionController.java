package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intervention")
public class InterventionController {
    private final InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createIntervention(@RequestParam Long adminId, String name, String description) {
        try {
            this.interventionService.createIntervention(name, description, adminId);
            return ResponseEntity.ok("Intervention created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/phase/{id}")
    public ResponseEntity<?> addPhase(@PathVariable Long id, PhaseName phaseName) {
        try {
            this.interventionService.addPhase(id, phaseName);
            return ResponseEntity.ok("Phase added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/phase/{id}")
    public ResponseEntity<?> getPhaseById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.interventionService.getPhaseById(id));
        } catch (PhaseNotFoundException p) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllInterventionsByPerson(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.interventionService.getAllInterventionsByPerson(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterventionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.interventionService.getInterventionById(id));
        } catch (InterventionNotFoundException i) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

