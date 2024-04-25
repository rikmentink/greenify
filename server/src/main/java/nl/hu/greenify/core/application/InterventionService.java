package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
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
        Person person = personService.getPersonById(adminId);
        if(person == null) {
            throw new IllegalArgumentException("Person with id " + adminId + " does not exist");
        }
        return interventionRepository.save(new Intervention(name, description, person));
    }

    public Intervention addPhase(Long id, PhaseName phaseName) {
        Intervention intervention = getInterventionById(id);
        if(intervention == null) {
            throw new IllegalArgumentException("Intervention with id " + id + " does not exist");
        }

        intervention.addPhase(phaseName);
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
        return interventionRepository.findInterventionsByAdmin(person);
    }

    public Intervention getInterventionById(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new InterventionNotFoundException(id));
    }
}
