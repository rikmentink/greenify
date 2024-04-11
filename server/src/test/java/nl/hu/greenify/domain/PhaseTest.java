package nl.hu.greenify.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Phase Domain Test")
public class PhaseTest {
    private Phase phase;

    @BeforeEach
    void setUp() {
        Intervention intervention = new Intervention("Garden", "Watering the plants");
        phase = new Phase("Garden", intervention);
    }

    @DisplayName("Phase should have a name")
    @Test
    void phaseName() {
        assertThrows(IllegalArgumentException.class, () -> new Phase(null, phase.getIntervention()));
    }
}
