package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.GreenifyRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class InterventionService {
    private final GreenifyRepository greenifyRepository;
    private final PhaseRepository phaseRepository;

    public InterventionService(GreenifyRepository greenifyRepository, PhaseRepository phaseRepository) {
        this.greenifyRepository = greenifyRepository;
        this.phaseRepository = phaseRepository;
    }

    public void addIntervention(String name, String description, Person person) {
        person.addIntervention(name, description);
        greenifyRepository.save(person);
    }

    public void addPhaseToIntervention(String name, PhaseName phaseName, Person person) {
        person.addPhaseToIntervention(name, phaseName);
        greenifyRepository.save(person);
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new PhaseNotFoundException("Phase with id " + id + " does not exist"));
    }

    // TODO: Move to separate PersonService
    public Person getPersonById(Long id) {
        Optional<Person> person = greenifyRepository.findById(id);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with id " + id + " does not exist");
        } else {
            return person.get();
        }
    }

    // TODO: Move to separate PersonService
    public Person getPersonByEmail(String email) {
        Optional<Person> person = greenifyRepository.findByEmail(email);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with username " + email + " does not exist");
        } else {
            return person.get();
        }
    }
}
