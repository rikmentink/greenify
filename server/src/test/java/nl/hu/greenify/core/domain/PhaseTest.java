package nl.hu.greenify.core.domain;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

@DisplayName("Phase Domain Test")
public class PhaseTest {
    private Intervention intervention;
    private Phase phase;

    @BeforeEach
    void setUp() {
        this.intervention = new Intervention(1L, "Intervention", "Description", new Person(), new ArrayList<>(), new ArrayList<>());
        phase = new Phase(1L, PhaseName.PLANNING, "Description", intervention, new ArrayList<>());
    }

    @DisplayName("Phase should have a name")
    @Test
    void phaseName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Phase.createPhase(this.intervention, null, "Description"));
    }

    @DisplayName("A phase should be able to add a survey")
    @Test
    void phaseAddSurvey() {
        Person person = new Person();
        phase.addSurvey(new Survey(1L, phase, new ArrayList<>(), person));
    }
}
