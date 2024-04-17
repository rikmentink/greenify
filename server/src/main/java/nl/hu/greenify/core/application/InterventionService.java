package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public void createIntervention(String name, String description, Long adminId) {
        Person admin = personService.getPersonById(adminId);
        interventionRepository.save(new Intervention(name, description, admin));
    }


    public void addPhase(Long id, PhaseName phaseName) {
        Intervention intervention = getInterventionById(id);
        intervention.addPhase(phaseName);
        interventionRepository.save(intervention);
        }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new PhaseNotFoundException("Phase with id " + id + " does not exist"));
    }

    public List<Intervention> getAllInterventionsByPerson(Person person) {
        return interventionRepository.findByPerson(person);
    }

    public Intervention getInterventionById(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new InterventionNotFoundException(id));
    }
}
