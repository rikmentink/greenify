package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.ResponseRepository;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.Template;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;

@SpringBootTest
@DisplayName("Survey Service Integration Test")
public class SurveyServiceIntegrationTest {
    private static final Long SURVEY_ID = 1L;
    private static final Long PERSON_ID = 1L;
    private static final Long PHASE_ID = 1L;
    private static final Long SUBFACTOR_ID = 1L;
    private static final Long SUBFACTOR_ANSWERED_ID = 2L;
    private static final Long RESPONSE_ID = 1L;

    private Template template;
    private Survey survey;
    private SubmitResponseDto answer;

    @Autowired
    private SurveyService surveyService;
    @MockBean
    private PersonService personService;
    @MockBean
    private InterventionService interventionService;
    @MockBean
    private SurveyRepository surveyRepository;
    @MockBean
    private ResponseRepository responseRepository;
    @MockBean
    private TemplateRepository templateRepository;

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

    /**
     * submitResponse tests
     */
    @Test
    @DisplayName("Submitting a response should add the response to the survey")
    void submitResponse() {
        Response response = surveyService.submitResponse(SURVEY_ID, this.answer);
        assertEquals(survey.getSubfactorById(SUBFACTOR_ID).getResponse(), response);
    }

    @Test
    @DisplayName("Submitting a response should save the response to the repository")
    void submitResponseSaveResponse() {
        Response response = surveyService.submitResponse(SURVEY_ID, this.answer);
        verify(responseRepository).save(response);
    }

    @Test
    @DisplayName("Submitting a response should save the updated survey to the repository")
    void submitResponseSaveSurvey() {
        surveyService.submitResponse(SURVEY_ID, this.answer);
        verify(surveyRepository).save(survey);
    }

    @Test
    @DisplayName("Submitting a response for a non-existing survey should throw a SurveyNotFoundException")
    void submitResponseForNonExistingSurvey() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.empty());
        assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.submitResponse(SURVEY_ID, this.answer)
        );
    }

    @Test
    @DisplayName("Submitting a response for a non-existing subfactor should throw an IllegalArgumentException")
    void submitResponseForNonExistingSubfactor() {
        this.answer = new SubmitResponseDto(3L, FacilitatingFactor.AGREE, Priority.PRIORITY, "Comment");
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.submitResponse(SURVEY_ID, this.answer)
        );
    }

    @Test
    @DisplayName("Submitting a response for a subfactor that has already been answered should update the response")
    void submitResponseForAnsweredSubfactor() {
        surveyService.submitResponse(SURVEY_ID, this.answer);
        this.answer = new SubmitResponseDto(SUBFACTOR_ANSWERED_ID, FacilitatingFactor.AGREE, Priority.PRIORITY, "Comment");
        Response response = surveyService.submitResponse(SURVEY_ID, this.answer);
        assertEquals(survey.getSubfactorById(SUBFACTOR_ANSWERED_ID).getResponse(), response);
    }

    @BeforeEach
    void setup() {
        var person = new Person(PERSON_ID, "John", "Doe", "john@example.com", new ArrayList<>());
        var phase = new Phase(PHASE_ID, PhaseName.EXECUTION);
        var subfactor = new Subfactor(SUBFACTOR_ID, "Subfactor", 1, true);
        var subfactorWithResponse = new Subfactor(SUBFACTOR_ANSWERED_ID, "Subfactor 2", 2, false);
        var response = new Response(RESPONSE_ID, 0, "Comment", FacilitatingFactor.AGREE, Priority.PRIORITY, subfactorWithResponse);
        var factor = new Factor(1L, "Factor", 1, List.of(subfactor, subfactorWithResponse));
        var category = new Category(1L, "Category", "", "", List.of(factor));

        this.template = new Template(1L, "Template", "Description", 1, List.of(category));
        this.survey = new Survey(SURVEY_ID, phase, List.of(category), person);
        this.answer = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.AGREE, Priority.PRIORITY, "Comment");

        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(template));
        when(personService.getPersonById(PERSON_ID)).thenReturn(person);
        when(interventionService.getPhaseById(PHASE_ID)).thenReturn(phase);
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(survey));
        when(responseRepository.save(any(Response.class))).thenReturn(response);
    }
}
