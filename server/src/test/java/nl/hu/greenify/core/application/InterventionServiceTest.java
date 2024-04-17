package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.data.PhaseRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InterventionServiceTest {
    private final InterventionRepository interventionRepository = mock(InterventionRepository.class);
    private final PhaseRepository phaseRepository = mock(PhaseRepository.class);
    private final InterventionService interventionService = new InterventionService(interventionRepository, phaseRepository);
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "johndoe@gmail.com");
        interventionService.addIntervention("Garden", "Watering the plants", person);
        interventionService.addPhase("Garden", PhaseName.INITIATION, person);
    }

    @Test
    @DisplayName("A phase should not be added when the name of the intervention it belongs to is incorrect")
    void addPhaseToInterventionWithIncorrectInterventionName() {
        Assertions.assertThrows(NoSuchElementException.class, () -> interventionService.addPhase("Garden5", PhaseName.INITIATION, person));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the phase is incorrect")
    void addPhaseToInterventionWithIncorrectPhaseName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interventionService.addPhase("Garden", null));
    }
}
