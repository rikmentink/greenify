package nl.hu.greenify.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Intervention Domain Test")
public class InterventionTest {
    private Intervention intervention; //For future tests

    @BeforeEach
    void setUp() {
        Person person = new Person("John", "Doe", "johnDoe@gmail.com");
        intervention = new Intervention("Garden", "Watering the plants", person);
    }

    @DisplayName("Intervention should have a name")
    @Test
    void interventionName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Intervention(null, "Watering the plants", new Person("John", "Doe", "johnDoe@gmail.com")));
    }

    @DisplayName("An intervention should be able to add a phase")
    @Test
    void interventionAddPhase() {
        intervention.addPhase(PhaseName.INITIATION);
    }

    @DisplayName("An intervention should not be able to add an invalid phase")
    @Test
    void interventionAddInvalidPhase() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> intervention.addPhase(null));
    }

    @DisplayName("An intervention should accept when the exact same phase is added")
    @Test
    void interventionAddDuplicatePhase() {
        intervention.addPhase(PhaseName.INITIATION);
        intervention.addPhase(PhaseName.INITIATION);
        assertEquals(2, intervention.getPhases().size());
    }

    @DisplayName("An intervention should be able to have 3 phases")
    @Test
    void interventionAddThreePhases() {
        intervention.addPhase(PhaseName.INITIATION);
        intervention.addPhase(PhaseName.PLANNING);
        intervention.addPhase(PhaseName.EXECUTION);
    }

}
