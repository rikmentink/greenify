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
}
