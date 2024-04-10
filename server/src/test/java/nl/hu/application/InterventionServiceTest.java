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
        user = new User("John", "Doe", "johndoe@gmail.com");
        interventionService.addIntervention("Garden", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants", user);
    }

    @Test
    @DisplayName("Intervention should be added to the user")
    void addIntervention() {
        assertEquals(1, user.getInterventions().size());
    }

    @Test
    @DisplayName("Phase should be added to the intervention")
    void addPhaseToIntervention() {
        System.out.println(user.getInterventions());
        assertEquals(1, user.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("Multiple phases should be added to multiple interventions")
    void addMultiplePhasesToInterventions() {
        interventionService.addIntervention("Garden2", "Watering the plants", user);
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
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", "Watering the plants", user));
    }

    @Test
    @DisplayName("More than 3 phases should not be added to the same intervention")
    void addMoreThanThreePhasesToIntervention() {
        interventionService.addPhaseToIntervention("Garden", "Watering the plants2", user);
        interventionService.addPhaseToIntervention("Garden", "Watering the plants3", user);
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", "Watering the plants4", user));
    }

    @Test
    @DisplayName("The same phase can only be added to another intervention")
    void addDuplicatePhaseToAnotherIntervention() {
        interventionService.addIntervention("Garden2", "Watering the plants", user);
        interventionService.addPhaseToIntervention("Garden2", "Watering the plants", user);
       assertAll(
               () -> assertEquals(1, user.getInterventions().get(0).getPhases().size()),
               () -> assertEquals(1, user.getInterventions().get(1).getPhases().size())
       );
    }

    @Test
    @DisplayName("A phase should not be added when the name of the intervention it belongs to is incorrect")
    void addPhaseToInterventionWithIncorrectInterventionName() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden5", "Watering the plants", user));
    }

}
