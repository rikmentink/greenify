package nl.hu.greenify.core.application;

import nl.hu.greenify.core.application.exceptions.InterventionNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.data.PhaseRepository;

import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.security.application.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Intervention Service Test")
public class InterventionServiceTest {
    private final InterventionRepository interventionRepository = mock(InterventionRepository.class);
    private final PhaseRepository phaseRepository = mock(PhaseRepository.class);
    private final PersonService personService = mock(PersonService.class);
    private final PersonRepository personRepository = mock(PersonRepository.class);
    private final AccountService accountService = mock(AccountService.class);
    private final SurveyService surveyService = mock(SurveyService.class);
    private final InterventionService interventionService = new InterventionService(accountService, personService, surveyService, interventionRepository, phaseRepository);
    Person person;
    Intervention intervention;
    Phase phase;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "firstName", "lastName", "username@gmail.com", new ArrayList<>());
        intervention = new Intervention(1L, "Intervention", "Intervention description", person, new ArrayList<>(), new ArrayList<>());
        phase = new Phase(1L, PhaseName.EXECUTION, "Description", intervention, new ArrayList<>());

        when(personService.getPersonById(1L)).thenReturn(person);
        when(interventionRepository.save(intervention)).thenReturn(intervention);
        when(personRepository.save(person)).thenReturn(person);
        when(phaseRepository.save(any(Phase.class))).thenReturn(phase);
        when(interventionRepository.findInterventionsByAdmin(person)).thenReturn(new ArrayList<>(List.of(intervention)));
        when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
        when(interventionRepository.findById(intervention.getId())).thenReturn(Optional.ofNullable(intervention));
        when(phaseRepository.findById(1L)).thenReturn(Optional.of(phase));
        when(interventionRepository.findInterventionsByAdmin(person)).thenReturn(new ArrayList<>(List.of(intervention)));
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
        interventionService.addPhase(1L, PhaseName.EXECUTION, "Description");
        verify(interventionRepository).save(intervention);
    }

    @DisplayName("When adding a phase to an intervention, a survey should be created for each participant")
    @Test
    void addPhaseShouldCreateSurveys() {
        interventionService.addPhase(1L, PhaseName.EXECUTION, "Description");
        verify(surveyService).createSurveysForParticipants(any(), eq(intervention.getParticipants()));
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
        assertEquals(interventionService.getAllInterventionsByPerson(person.getId()), List.of(intervention));
    }
}
