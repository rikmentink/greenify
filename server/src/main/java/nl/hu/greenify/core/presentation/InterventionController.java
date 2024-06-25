package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.InterventionService;
import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.presentation.dto.CreateInterventionDto;
import nl.hu.greenify.core.presentation.dto.CreatePhaseDto;

import nl.hu.greenify.core.presentation.dto.InterventionDto;
import nl.hu.greenify.core.presentation.dto.PhaseDto;
import nl.hu.greenify.security.application.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intervention")
public class InterventionController extends Controller {
    private final InterventionService interventionService;
    private final PersonService personService;
    private final AccountService accountService;

    public InterventionController(InterventionService interventionService, PersonService personService, AccountService accountService) {
        this.interventionService = interventionService;
        this.personService = personService;
        this.accountService = accountService;
    }

    /**
     * Intervention endpoints
     */

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getInterventionById(@PathVariable("id") Long id) {
        Person currentPerson = accountService.getCurrentPerson();
        return this.createResponse(InterventionDto.fromEntity(this.interventionService.getInterventionById(id), currentPerson));
    }

//    @Secured({"ROLE_MANAGER", "ROLE_VUMEDEWERKER"})
    @PostMapping(value="/{id}/person/{personId}", produces="application/json")
    public ResponseEntity<?> addParticipant(@PathVariable("id") Long id, @PathVariable("personId") Long personId) {
        return this.createResponse(this.interventionService.addParticipant(id, personId), HttpStatus.CREATED);
    }

//    @Secured({"ROLE_MANAGER", "ROLE_VUMEDEWERKER"})
    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllInterventionsByPerson(@PathVariable("id") Long id) {
        List<Intervention> i = this.interventionService.getAllInterventionsByPerson(id);
        Person person = this.personService.getPersonById(id);

        return this.createResponse(InterventionDto.fromEntities(i, person));
    }

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
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

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/phase/{id}", produces="application/json")
    public ResponseEntity<?> getPhaseById(@PathVariable("id") Long id) {
        return this.createResponse(this.interventionService.getPhaseById(id));
    }
//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{id}/phases", produces="application/json")
    public ResponseEntity<?> getPhasesByInterventionId(@PathVariable("id") Long id) {
        return this.createResponse(PhaseDto.fromEntities(this.interventionService.getPhasesByIntervention(id)));
    }

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{interventionId}/phase/{phaseId}", produces="application/json")
    public ResponseEntity<?> getPhaseProgress(@PathVariable("interventionId") Long interventionId, @PathVariable("phaseId") Long phaseId) {
        return this.createResponse(this.interventionService.getPhaseProgress(interventionId, phaseId));
    }

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @PostMapping(value="/{id}/phase", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addPhase(@PathVariable("id") Long id, @RequestBody CreatePhaseDto createPhaseDto) {
        return this.createResponse(this.interventionService.addPhase(id, createPhaseDto.getPhaseName(), createPhaseDto.getDescription()), HttpStatus.CREATED);
    }
}
