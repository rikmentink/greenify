package nl.hu.domain;

import domain.Intervention;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(IllegalArgumentException.class, () -> new Intervention(null, "Watering the plants"));
    }

    @DisplayName("Intervention should have a description")
    @Test
    void interventionDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Intervention("Garden", null));
    }

    @DisplayName("An intervention should be able to add a phase")
    @Test
    void interventionAddPhase() {
        intervention.addPhase("Watering the plants");
    }

    @DisplayName("An intervention should not be able to add an invalid phase")
    @Test
    void interventionAddInvalidPhase() {
        assertThrows(IllegalArgumentException.class, () -> intervention.addPhase(null));
    }

    @DisplayName("An intervention should not accept when the exact same phase is added")
    @Test
    void interventionAddDuplicatePhase() {
        intervention.addPhase("Watering the plants");
        assertThrows(IllegalArgumentException.class, () -> intervention.addPhase("Watering the plants"));
    }

    @DisplayName("More than 3 phases for each intervention is not allowed")
    @Test
    void interventionAddMoreThanThreePhases() {
        intervention.addPhase("Watering the plants");
        intervention.addPhase("Watering the plants2");
        intervention.addPhase("Watering the plants3");
        assertThrows(IllegalArgumentException.class, () -> intervention.addPhase("Watering the plants4"));
    }

    @DisplayName("An intervention should be able to have 3 phases")
    @Test
    void interventionAddThreePhases() {
        intervention.addPhase("Watering the plants");
        intervention.addPhase("Watering the plants2");
        intervention.addPhase("Watering the plants3");
    }

}
