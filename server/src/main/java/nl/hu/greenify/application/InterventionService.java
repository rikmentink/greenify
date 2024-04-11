package nl.hu.greenify.application;

import nl.hu.greenify.data.GreenifyRepository;
import nl.hu.greenify.domain.Person;
import org.springframework.stereotype.Service;


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

    public void addPhaseToIntervention(String name, String phaseName, Person person) {
        person.addPhaseToIntervention(name, phaseName);
        greenifyRepository.save(person);
    }
}
