package nl.hu.greenify.domain;

import nl.hu.greenify.domain.Intervention;
import nl.hu.greenify.domain.Phase;
import nl.hu.greenify.domain.enums.PhaseName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Phase Domain Test")
public class PhaseTest {
    private Phase phase;

    @BeforeEach
    void setUp() {
        phase = new Phase(PhaseName.PLANNING);
    }

    @DisplayName("Phase should have a name")
    @Test
    void phaseName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Phase(null));
    }
}
