package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PersonRepository;

import nl.hu.greenify.core.data.PhaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class InterventionServiceIntegrationTest {

    @Autowired
    private InterventionService interventionService;
    @MockBean
    private InterventionRepository interventionRepository;
    @MockBean
    private PhaseRepository phaseRepository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("When fetching a phase with a valid id, it should be fetched from the repository")
    @Test
    void getPhaseByIdShouldFetch() {
        interventionService.getPhaseById(1L);
        verify(phaseRepository).findById(1L);
    }

    @DisplayName("When fetching all interventions by a person, they should be fetched from the repository")
    @Test
    void getAllInterventionsByPersonShouldFetch() {
        interventionService.getAllInterventionsByPerson(person);
        verify(interventionRepository).findByPerson(person);
    }

    @DisplayName("When fetching an intervention with a valid id, it should be fetched from the repository")
    @Test
    void getInterventionByIdShouldFetch() {
        interventionService.getInterventionById(1L);
        verify(interventionRepository).findById(1L);
    }

    @DisplayName("When fetching an intervention with an invalid id, it should throw an exception")
    @Test
    void getInterventionByIdShouldThrowException() {
        assertThrows(InterventionNotFoundException.class, () -> interventionService.getInterventionById(2L));
    }

    @DisplayName("When fetching a phase with an invalid id, it should throw an exception")
    @Test
    void getPhaseByIdShouldThrowException() {
        assertThrows(PhaseNotFoundException.class, () -> interventionService.getPhaseById(2L));
    }

    @DisplayName("When fetching all interventions by a person and there are none, a list should be returned")
    @Test
    void getAllInterventionsByPersonShouldReturnEmptyList() {
        assertEquals(interventionService.getAllInterventionsByPerson(person), List.of(i));
    }
}
