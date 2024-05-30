package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PersonRepository;

import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Intervention Service Integration Test")
public class InterventionServiceIntegrationTest {

    @Autowired
    private InterventionService interventionService;
    @MockBean
    private InterventionRepository interventionRepository;
    @MockBean
    private PhaseRepository phaseRepository;
    @MockBean
    private PersonRepository personRepository;
    private Person person;
    private Intervention i;


    @BeforeEach
    void setUp() {
        person = new Person(1L, "firstName", "lastName", "johndoe@gmail.com", new ArrayList<>());
        person.setId(1L);
        i = new Intervention("Intervention", "Intervention description", person);
        i.setId(1L);
        Phase phase = new Phase(1L, PhaseName.PLANNING);

        when(interventionRepository.findById(1L)).thenReturn(java.util.Optional.of(i));
        when(phaseRepository.findById(1L)).thenReturn(java.util.Optional.of(phase));
        when(interventionRepository.findInterventionsByAdmin(person)).thenReturn(List.of(i));
        when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));

    }

    @DisplayName("Creating an intervention should be possible")
    @Test
    void createIntervention() {
        assertDoesNotThrow(() -> interventionService.createIntervention("Intervention", "Intervention description", person.getId()));
    }

    @DisplayName("When creating an intervention with an invalid admin id, it should throw an exception")
    @Test
    void createInterventionShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.createIntervention("Intervention", "Intervention description", 2L));
    }

    @DisplayName("When adding a phase to an intervention, it should be added to the intervention")
    @Test
    void addPhase() {
        interventionService.addPhase(1L, PhaseName.PLANNING);
        verify(interventionRepository).save(i);
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
        interventionService.getAllInterventionsByPerson(person.getId());
        verify(interventionRepository).findInterventionsByAdmin(person);
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
        assertEquals(interventionService.getAllInterventionsByPerson(person.getId()), List.of(i));
    }
}
