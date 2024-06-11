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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterventionService {
    private final InterventionRepository interventionRepository;
    private final PersonService personService;
    private final PhaseRepository phaseRepository;

    public InterventionService(InterventionRepository interventionRepository, PhaseRepository phaseRepository, PersonService personService) {
        this.interventionRepository = interventionRepository;
        this.personService = personService;
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

    public Intervention addParticipant(Long id, Long personId) {
        Intervention intervention = getInterventionById(id);
        Person person = personService.getPersonById(personId);

        if(person == null) {
            throw new IllegalArgumentException("Person with id " + personId + " does not exist");
        }

        intervention.addParticipant(person);
        return interventionRepository.save(intervention);
    }

    public Intervention addPhase(Long id, PhaseName phaseName) {
        Intervention intervention = getInterventionById(id);
        if(intervention == null) {
            throw new IllegalArgumentException("Intervention with id " + id + " does not exist");
        }
        
        Phase phase = Phase.createPhase(phaseName);
        phaseRepository.save(phase);

        intervention.addPhase(phase);
        return interventionRepository.save(intervention);
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new PhaseNotFoundException("Phase with id " + id + " does not exist"));
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
