package nl.hu.greenify.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

@DisplayName("Intervention Domain Test")
public class InterventionTest {
    private Intervention intervention;
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "John", "Doe", "johndoe@gmail.com", new ArrayList<>());
        intervention = new Intervention("Garden", "Watering the plants", person);
    }

    @DisplayName("Intervention should have a name")
    @Test
    void interventionName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Intervention(null, "Watering the plants", person));
    }

    @DisplayName("An intervention should be able to add a phase")
    @Test
    void interventionAddPhase() {
        Phase phase = new Phase(1L, PhaseName.INITIATION);
        intervention.addPhase(phase);
    }

    @DisplayName("An intervention should not be able to add an invalid phase")
    @Test
    void interventionAddInvalidPhase() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> intervention.addPhase(null));
    }

    @DisplayName("An intervention should accept when the exact same phase is added")
    @Test
    void interventionAddDuplicatePhase() {
        Phase phase = new Phase(1L, PhaseName.INITIATION);
        intervention.addPhase(phase);
        intervention.addPhase(phase);
        assertEquals(2, intervention.getPhases().size());
    }

    @DisplayName("An intervention should be able to have multiple phases")
    @Test
    void interventionAddThreePhases() {
        Phase phase1 = new Phase(1L, PhaseName.INITIATION);
        Phase phase2 = new Phase(2L, PhaseName.PLANNING);
        Phase phase3 = new Phase(3L, PhaseName.EXECUTION);
        intervention.addPhase(phase1);
        intervention.addPhase(phase2);
        intervention.addPhase(phase3);
    }

    @DisplayName("An intervention should have an admin")
    @Test
    void interventionAdmin() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Intervention("Garden", "Watering the plants", null));
    }

    @DisplayName("An intervention should be able to add a participant")
    @Test
    void interventionAddParticipant() {
        Person participant = new Person(2L, "Jane", "Doe", "Janedoe@gmail.com", new ArrayList<>());
        intervention.addParticipant(participant);
        assertEquals(1, intervention.getParticipants().size());
    }

    @DisplayName("An intervention should be able to add multiple participants")
    @Test
    void interventionAddMultipleParticipants() {
        Person participant1 = new Person(2L, "Jane", "Doe", "Janedoe@gmail.com", new ArrayList<>());
        Person participant2 = new Person(3L, "Jack", "Doe", "jackdoe@gmail.com", new ArrayList<>());
        intervention.addParticipant(participant1);
        intervention.addParticipant(participant2);
        assertEquals(2, intervention.getParticipants().size());
    }
}
