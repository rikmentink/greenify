package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.enums.PhaseName;

@SpringBootTest
@DisplayName("Survey Service Integration Test")
public class SurveyServiceIntegrationTest {
    private final Long SURVEY_ID = 1L;
    private final Long PERSON_ID = 1L;
    private final Long PHASE_ID = 1L;

    @Autowired
    private SurveyService surveyService;
    @MockBean
    private SurveyRepository surveyRepository;
    @MockBean
    private PersonService personService;
    @MockBean
    private InterventionService interventionService;
    private Survey survey;

    /**
     * createSurvey tests
     */
    @Test
    @DisplayName("Creating a survey should create a valid survey and save it in the repository")
    void createSurvey() {
        Survey createdSurvey = surveyService.createSurvey(PERSON_ID, PHASE_ID);
        assertNotNull(createdSurvey);
        assertEquals(survey, createdSurvey);
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    @DisplayName("Creating a survey with a non-existing person should throw an IllegalArgumentException")
    void createSurveyWithNonExistingPerson() {
        when(personService.getPersonById(PERSON_ID)).thenThrow(new IllegalArgumentException("Error"));
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(PERSON_ID, PHASE_ID)
        );
    }

    @Test
    @DisplayName("Creating a survey with a non-existing phase should throw an IllegalArgumentException")
    void createSurveyWithNonExistingPhase() {
        when(interventionService.getPhaseById(PHASE_ID)).thenThrow(new IllegalArgumentException("Error"));
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(PERSON_ID, PHASE_ID)
        );
    }

    @BeforeEach
    void setup() {
        Person person = new Person("John", "Doe", "john@example.com", new ArrayList<>());
        Phase phase = new Phase(PhaseName.EXECUTION);
        this.survey = new Survey(SURVEY_ID, phase, new ArrayList<>(), person);

        when(personService.getPersonById(PERSON_ID)).thenReturn(person);
        when(interventionService.getPhaseById(PHASE_ID)).thenReturn(phase);
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
    }
}
