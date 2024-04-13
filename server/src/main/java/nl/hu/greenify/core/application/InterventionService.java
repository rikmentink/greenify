package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.GreenifyRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class InterventionService {
    private final GreenifyRepository greenifyRepository;

    public InterventionService(GreenifyRepository greenifyRepository) {
        this.greenifyRepository = greenifyRepository;
    }

    public void addIntervention(String name, String description, Person person) {
        person.addIntervention(name, description);
        greenifyRepository.save(person);
    }

    public void addPhaseToIntervention(String name, PhaseName phaseName, Person person) {
        person.addPhaseToIntervention(name, phaseName);
        greenifyRepository.save(person);
    }

    public Person getPersonById(Long id) {
        Optional<Person> person = greenifyRepository.findById(id);
        if(person.isEmpty()) {
            throw new IllegalArgumentException("Person with id " + id + " does not exist");
        } else {
            return person.get();
        }
    }
}
