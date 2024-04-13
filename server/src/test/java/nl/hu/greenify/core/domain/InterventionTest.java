package nl.hu.greenify.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

@DisplayName("Intervention Domain Test")
public class InterventionTest {
    private Intervention intervention; //For future tests

    @BeforeEach
    void setUp() {
        intervention = new Intervention("Garden", "Watering the plants");
    }

    @DisplayName("Intervention should have a name")
    @Test
    void interventionName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Intervention(null, "Watering the plants"));
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

    @DisplayName("An intervention should not accept when the exact same phase is added")
    @Test
    void interventionAddDuplicatePhase() {
        intervention.addPhase(PhaseName.INITIATION);
        Assertions.assertThrows(IllegalArgumentException.class, () -> intervention.addPhase(PhaseName.INITIATION));
    }

    @DisplayName("An intervention should be able to have 3 phases")
    @Test
    void interventionAddThreePhases() {
        intervention.addPhase(PhaseName.INITIATION);
        intervention.addPhase(PhaseName.PLANNING);
        intervention.addPhase(PhaseName.EXECUTION);
    }

}
