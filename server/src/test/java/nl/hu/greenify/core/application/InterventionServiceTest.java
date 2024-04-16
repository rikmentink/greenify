package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.data.GreenifyRepository;
import nl.hu.greenify.core.data.PhaseRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;

public class InterventionServiceTest {
    private final GreenifyRepository greenifyRepository = mock(GreenifyRepository.class);
    private final PhaseRepository phaseRepository = mock(PhaseRepository.class);
    private final InterventionService interventionService = new InterventionService(greenifyRepository, phaseRepository);
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "johndoe@gmail.com");
        interventionService.addIntervention("Garden", "Watering the plants", person);
        interventionService.addPhaseToIntervention("Garden", PhaseName.INITIATION, person);
    }

    @Test
    @DisplayName("Intervention should be added to the user")
    void addIntervention() {
        Assertions.assertEquals(1, person.getInterventions().size());
    }

    @Test
    @DisplayName("Phase should be added to the intervention")
    void addPhaseToIntervention() {
        Assertions.assertEquals(1, person.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("Multiple phases should be added to multiple interventions")
    void addMultiplePhasesToInterventions() {
        interventionService.addIntervention("Garden2", "Watering the plants", person);
        interventionService.addPhaseToIntervention("Garden2", PhaseName.INITIATION, person);
        interventionService.addPhaseToIntervention("Garden2", PhaseName.PLANNING, person);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, person.getInterventions().get(0).getPhases().size()),
                () -> Assertions.assertEquals(2, person.getInterventions().get(1).getPhases().size())
        );
    }

    @Test
    @DisplayName("Phase with the same name should be added to the same intervention")
    void addDuplicatePhaseToIntervention() {
        interventionService.addPhaseToIntervention("Garden", PhaseName.INITIATION, person);
        Assertions.assertEquals(2, person.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("The same phase can be added to another intervention")
    void addDuplicatePhaseToAnotherIntervention() {
        interventionService.addIntervention("Garden2", "Watering the plants", person);
        interventionService.addPhaseToIntervention("Garden2", PhaseName.INITIATION, person);
       Assertions.assertAll(
               () -> Assertions.assertEquals(1, person.getInterventions().get(0).getPhases().size()),
               () -> Assertions.assertEquals(1, person.getInterventions().get(1).getPhases().size())
       );
    }

    @Test
    @DisplayName("A phase should not be added when the name of the intervention it belongs to is incorrect")
    void addPhaseToInterventionWithIncorrectInterventionName() {
        Assertions.assertThrows(NoSuchElementException.class, () -> interventionService.addPhaseToIntervention("Garden5", PhaseName.INITIATION, person));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the phase is incorrect")
    void addPhaseToInterventionWithIncorrectPhaseName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", null, person));
    }
}
