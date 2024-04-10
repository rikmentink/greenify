package nl.hu.application;

import application.InterventionService;
import data.GreenifyRepository;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InterventionServiceTest {
    private final GreenifyRepository greenifyRepository = mock(GreenifyRepository.class);
    private final InterventionService interventionService = new InterventionService(greenifyRepository);
    private User user;

    @BeforeEach
    void setUp() {
        interventionService.addIntervention("Garden", "Watering the plants", user);
        user = new User("John", "Doe", "johndoe@gmail.com");
    }

    @Test
    @DisplayName("Intervention should be added to the user")
    void addIntervention() {
        interventionService.addIntervention("Garden", "Watering the plants", user);
        assertEquals(1, user.getInterventions().size());
    }

    @Test
    @DisplayName("Phase should be added to the intervention")
    void addPhaseToIntervention() {
        interventionService.addIntervention("Garden", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants", user);
        System.out.println(user.getInterventions());
        assertEquals(1, user.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("Multiple phases should be added to multiple interventions")
    void addMultiplePhasesToInterventions() {
        interventionService.addIntervention("Garden1", "Watering the plants", user);
        interventionService.addIntervention("Garden2", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden1", "Watering the plants1", user);
        interventionService.addPhaseToIntervention("Garden2", "Watering the plants2", user);
        interventionService.addPhaseToIntervention("Garden2", "Watering the plants3", user);
        assertAll(
                () -> assertEquals(1, user.getInterventions().get(0).getPhases().size()),
                () -> assertEquals(2, user.getInterventions().get(1).getPhases().size())
        );
    }

    @Test
    @DisplayName("Phase with the same name should not be added to the same intervention")
    void addDuplicatePhaseToIntervention() {
        interventionService.addIntervention("Garden", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants", user);
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", "Watering the plants", user));
    }

    @Test
    @DisplayName("More than 3 phases should not be added to the same intervention")
    void addMoreThanThreePhasesToIntervention() {
        interventionService.addIntervention("Garden", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants2", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants3", user);
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", "Watering the plants4", user));
    }

    @Test
    @DisplayName("The same phase can only be added to another intervention")
    void addDuplicatePhaseToAnotherIntervention() {
        interventionService.addIntervention("Garden1", "Watering the plants", user);
        interventionService.addIntervention("Garden2", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden1", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden2", "Watering the plants", user);
       assertAll(
               () -> assertEquals(1, user.getInterventions().get(0).getPhases().size()),
               () -> assertEquals(1, user.getInterventions().get(1).getPhases().size())
       );
    }

}
