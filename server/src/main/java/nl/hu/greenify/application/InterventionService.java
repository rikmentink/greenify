package nl.hu.greenify.application;

import nl.hu.greenify.data.GreenifyRepository;
import nl.hu.greenify.domain.User;
import org.springframework.stereotype.Service;


@Service
public class InterventionService {
    private final GreenifyRepository greenifyRepository;

    public InterventionService(GreenifyRepository greenifyRepository) {
        this.greenifyRepository = greenifyRepository;
    }

    public void addIntervention(String name, String description, User user) {
        user.addIntervention(name, description);
        greenifyRepository.save(user);
    }

    public void addPhaseToIntervention(String name, String phaseName, User user) {
        user.addPhaseToIntervention(name, phaseName);
        greenifyRepository.save(user);
    }
}
