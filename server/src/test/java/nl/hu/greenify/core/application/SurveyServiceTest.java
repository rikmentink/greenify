package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.ResponseRepository;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    private static final Long PERSON_ID = 1L;
    private static final Long PHASE_ID = 1L;
    private static final Long SURVEY_ID = 1L;
    private static final Long CATEGORY_ID = 1L;
    private static final Long FACTOR_ID = 1L;
    private static final Long SUBFACTOR_ID = 1L;

    private Person person;
    private Phase phase;
    private Survey survey;

    @Mock
    private InterventionService interventionService;

    @Mock
    private PersonService personService;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private TemplateRepository templateRepository;

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private SurveyService surveyService;

    @BeforeEach
    public void setup() {
        this.person = new Person(PERSON_ID, "John", "Doe", "johndoe@gmail.com", new ArrayList<>());

        var intervention = new Intervention(1L, "Intervention", "Description", this.person, new ArrayList<>(), Arrays.asList(this.person));
        this.phase = new Phase(PHASE_ID, PhaseName.INITIATION, "Description", intervention, new ArrayList<>());

        var subfactor = new Subfactor(SUBFACTOR_ID, "Subfactor", 1, true);
        var factor = new Factor(FACTOR_ID, "Factor", 1, List.of(subfactor));
        var category = new Category(CATEGORY_ID, "Category", "", "", List.of(factor));
        this.survey = new Survey(SURVEY_ID, this.phase, List.of(category), this.person);
    }

    /**
     * getAllSurveys tests
     */
    @Test
    @DisplayName("When fetching all surveys, they should be fetched from the repository")
    public void getAllSurveysShouldFetch() {
        surveyService.getAllSurveys();
        verify(surveyRepository).findAll();
    }

    @Test
    @DisplayName("When fetching all surveys and there are none, an empty list should be returned")
    public void getAllSurveysShouldReturnEmptyList() {
        assertEquals(surveyService.getAllSurveys(), new ArrayList<>());
    }
    
    /**
     * getSurvey tests
     */
    @Test
    @DisplayName("When fetching a survey with a valid id, it should be fetched from the repository")
    public void getSurveyShouldFetch() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));

        surveyService.getSurvey(SURVEY_ID);
        verify(surveyRepository).findById(SURVEY_ID);
    }

    @Test
    @DisplayName("When fetching a survey with an invalid id, it should throw an exception")
    public void getSurveyShouldThrowException() {
        assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.getSurvey(2L)
        );
    }

    /**
     * getQuestions tests
     */
    @Test
    @DisplayName("When getting questions for a survey, the survey should be fetched")
    public void getQuestionsShouldFetchSurvey() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));

        surveyService.getQuestions(SURVEY_ID, 1L, 1, 1000);
        verify(surveyRepository).findById(SURVEY_ID);
    }

    @Test
    @DisplayName("When getting questions for a survey with an invalid id, it should throw an exception")
    public void getQuestionsShouldThrowException() {
        assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.getQuestions(2L, 1L, 1, 1000)
        );
    }

    @Test
    @DisplayName("When getting questions for a survey with an invalid category id, it should return all categories")
    public void getQuestionsShouldReturnAllCategories() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));
        
        var questionSet = surveyService.getQuestions(SURVEY_ID, 0L, 1, 1000);
        assertEquals(questionSet.getCategories().size(), 1);
    }

    /**
     * createSurvey tests
     */
    @Test
    @DisplayName("When creating a survey, it should be saved in the repository")
    // TODO: Fix this failure
    public void createSurveyShouldSave() {
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));
        var newPerson = new Person(2L, "John", "Doe", "johndoe@gmail.com", new ArrayList<>());

        surveyService.createSurvey(this.phase, newPerson);
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    @DisplayName("When creating a survey without a phase, it should throw an exception")
    public void createSurveyShouldThrowExceptionPhase() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(null, this.person)
        );
    }

    @Test
    @DisplayName("When creating a survey without a person, it should throw an exception")
    public void createSurveyShouldThrowExceptionPerson() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(phase, null)
        );
    }

    /**
     * submitResponse tests
     */
    @Test
    @DisplayName("When submitting a response, it should be saved in the repository")
    public void submitResponseShouldSave() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));

        SubmitResponseDto responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        Response response = surveyService.submitResponse(SURVEY_ID, responseData);
        assertTrue(survey.getSubfactorById(SUBFACTOR_ID).getResponse().equals(response));
    }

    @Test
    @DisplayName("When submitting a response with an existing response, it should be updated")
    public void submitResponseShouldUpdate() {
        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));

        SubmitResponseDto responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        Response response = surveyService.submitResponse(SURVEY_ID, responseData);

        responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        response = surveyService.submitResponse(SURVEY_ID, responseData);
        assertTrue(survey.getSubfactorById(SUBFACTOR_ID).getResponse().equals(response));
    }

    @Test
    @DisplayName("When submitting a response with an invalid survey id, it should throw an exception")
    public void submitResponseShouldThrowException() {
        SubmitResponseDto responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        assertThrows(
            SurveyNotFoundException.class,
            () -> surveyService.submitResponse(2L, responseData)
        );
    }

    /**
     * createDefaultTemplate tests
     */
    @Test
    @DisplayName("When creating a default template, it should return the active template if one already exists")
    public void createDefaultTemplateShouldReturnActive() throws Exception {
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));

        surveyService.createDefaultTemplate();
        verify(templateRepository).findFirstByOrderByVersionDesc();
    }

    @Test
    @DisplayName("When creating a default template and none exists, it should create one")
    public void createDefaultTemplateShouldCreate() throws Exception {
        when(templateRepository.count()).thenReturn(0L);
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));

        surveyService.createDefaultTemplate();
        verify(templateRepository).save(any(Template.class));
    }

    @Test
    @DisplayName("When deleting a response, it should be deleted the subfactor")
    public void deleteResponseShouldDelete() {
        // Mock a response
        Response response = mock(Response.class);

        // Assign the response to a subfactor that is part of the survey
        Subfactor subfactor = new Subfactor(1L, "Subfactor", 1, true);
        subfactor.setResponse(response);

        // Remaining setup of the survey
        Factor factor = new Factor(1L, "Factor", 1, List.of(subfactor));
        Category category = new Category(1L, "Category", "", "", List.of(factor));
        Survey survey = new Survey(1L, phase, List.of(category), person);

        // Mock the survey repository
        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));

        // Confirm the response is assigned to the subfactor
        assertTrue(subfactor.getResponse().equals(response));

        // Delete the response
        surveyService.deleteResponse(1L, 1L);

        // Confirm the response is removed from the subfactor
        assertTrue(subfactor.getResponse() == null);
    }

    private Template mockTemplate() {
        return mock(Template.class);
    }
}
