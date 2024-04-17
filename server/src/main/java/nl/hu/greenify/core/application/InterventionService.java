package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class InterventionService {
    private final PersonRepository personRepository;
    private final PhaseRepository phaseRepository;

    public InterventionService(PersonRepository personRepository, PhaseRepository phaseRepository) {
        this.personRepository = personRepository;
        this.phaseRepository = phaseRepository;
    }

    public void addPhase(String name, PhaseName phaseName, Person person) {
        person.addPhaseToIntervention(name, phaseName);
        personRepository.save(person);
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new PhaseNotFoundException("Phase with id " + id + " does not exist"));
    }
}
