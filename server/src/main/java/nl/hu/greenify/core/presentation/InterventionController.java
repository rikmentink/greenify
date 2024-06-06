package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.presentation.dto.CreateInterventionDto;
import nl.hu.greenify.core.presentation.dto.CreatePhaseDto;

import nl.hu.greenify.core.presentation.dto.InterventionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intervention")
public class InterventionController extends Controller {
    private final InterventionService interventionService;
    private final PersonService personService;

    public InterventionController(InterventionService interventionService, PersonService personService) {
        this.interventionService = interventionService;
        this.personService = personService;
    }

    /**
     * Intervention endpoints
     */

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getInterventionById(@PathVariable("id") Long id) {
        return this.createResponse(this.interventionService.getInterventionById(id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllInterventionsByPerson(@PathVariable("id") Long id) {
        List<Intervention> i = this.interventionService.getAllInterventionsByPerson(id);
        Person person = this.personService.getPersonById(id);

        return this.createResponse(InterventionDto.fromEntities(i, person));
    }

    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> createIntervention(@RequestBody CreateInterventionDto createInterventionDto) {
        return this.createResponse(
            this.interventionService.createIntervention(
                createInterventionDto.getName(),
                createInterventionDto.getDescription(),
                createInterventionDto.getAdminId()
            ),
            HttpStatus.CREATED
        );
    }

    /**
     * Phase endpoints
     */

    @GetMapping(value="/phase/{id}", produces="application/json")
    public ResponseEntity<?> getPhaseById(@PathVariable("id") Long id) {
        return this.createResponse(this.interventionService.getPhaseById(id));
    }

    @PostMapping(value="/{id}/phase", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addPhase(@PathVariable("id") Long id, @RequestBody CreatePhaseDto createPhaseDto) {
        return this.createResponse(this.interventionService.addPhase(id, createPhaseDto.getPhaseName()), HttpStatus.CREATED);
    }
}
