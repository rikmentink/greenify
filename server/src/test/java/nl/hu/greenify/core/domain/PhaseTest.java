package nl.hu.greenify.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

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
