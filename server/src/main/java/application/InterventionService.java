package application;

import data.GreenifyRepository;
import domain.User;
import org.springframework.stereotype.Service;

@Service
public class InterventionService { //Pure? DTO?
    GreenifyRepository greenifyRepository;

    public InterventionService(GreenifyRepository greenifyRepository) {
        this.greenifyRepository = greenifyRepository;
    }

    public void addIntervention(String name, String description, User user) {
        user.addIntervention(name, description);
    }


}