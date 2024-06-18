package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.presentation.dto.overview.PhaseProgressDto;
import nl.hu.greenify.security.application.AccountService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterventionService {
    private final AccountService accountService;
    private final PersonService personService;
    private final SurveyService surveyService;

    private final InterventionRepository interventionRepository;
    private final PhaseRepository phaseRepository;

    public InterventionService(AccountService accountService, PersonService personService, SurveyService surveyService,
            InterventionRepository interventionRepository, PhaseRepository phaseRepository) {
        this.accountService = accountService;
        this.personService = personService;
        this.surveyService = surveyService;
        this.interventionRepository = interventionRepository;
        this.phaseRepository = phaseRepository;
    }

    public Intervention createIntervention(String name, String description, Long adminId) {
        try {
            Person person = personService.getPersonById(adminId);
            return interventionRepository.save(Intervention.createIntervention(name, description, person));
        } catch (PersonNotFoundException e) {
            throw new IllegalArgumentException("Intervention should have an existing admin");
        }
    }

    public Phase addPhase(Long id, PhaseName phaseName) {
        Intervention intervention = getInterventionById(id);
        if(intervention == null) {
            throw new IllegalArgumentException("Intervention with id " + id + " does not exist");
        }
        
        Phase phase = Phase.createPhase(phaseName);
        phase = phaseRepository.save(phase);
        surveyService.createSurveysForParticipants(phase, intervention.getParticipants());

        intervention.addPhase(phase);
        interventionRepository.save(intervention);
        return phase;
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new PhaseNotFoundException("Phase with id " + id + " does not exist"));
    }

    public PhaseProgressDto getPhaseProgress(Long interventionId, Long phaseId) {
        Intervention intervention = this.getInterventionById(interventionId);
        Phase phase = this.getPhaseById(phaseId);

        if(!intervention.getPhases().contains(phase))
            throw new IllegalArgumentException("Phase with id " + phaseId + " is not part of intervention with id " + interventionId);

        Person person = accountService.getCurrentPerson();
        if(!intervention.getParticipants().contains(person) && !intervention.getAdmin().equals(person))
            throw new IllegalArgumentException("Person with id " + person.getId() + " is not part of intervention with id " + interventionId);
        
        if (intervention.getAdmin().equals(person)) {
            return PhaseProgressDto.fromEntities(intervention, phase, intervention.getParticipants());
        }
        
        return PhaseProgressDto.fromEntity(intervention, phase, person);
    }

    public List<Intervention> getAllInterventionsByPerson(Long id) {
        Person person = personService.getPersonById(id);
        if(person == null) {
            throw new IllegalArgumentException("Person with id " + id + " does not exist");
        }
        List<Intervention> i = interventionRepository.findInterventionsByAdmin(person);
        List<Intervention> i2 = interventionRepository.findInterventionsByParticipantsContains(person);
        i.addAll(i2);

        return i;
    }

    public Intervention getInterventionById(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new InterventionNotFoundException(id));
    }
}
