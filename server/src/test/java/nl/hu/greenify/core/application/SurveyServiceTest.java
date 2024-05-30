package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.CategoryRepository;
import nl.hu.greenify.core.data.ResponseRepository;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;

public class SurveyServiceTest {
    private static final Long SURVEY_ID = 1L;
    private static final Long SUBFACTOR_ID = 1L;
    private static final Long PHASE_ID = 1L;

    private Subfactor subfactor;
    private Factor factor;
    private Category category;
    private Survey survey;
    private Phase phase;

    private SurveyService surveyService;
    private SurveyRepository surveyRepository;
    private TemplateRepository templateRepository;
    private InterventionService interventionService;
    private PersonService personService;
    private Person person;
    private Intervention intervention;

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
        when(surveyRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(surveyService.getAllSurveys(), new ArrayList<>());
    }
    
    /**
     * getSurvey tests
     */
    @Test
    @DisplayName("When fetching a survey with a valid id, it should be fetched from the repository")
    public void getSurveyShouldFetch() {
        surveyService.getSurvey(1L);
        verify(surveyRepository).findById(1L);
    }

    @Test
    @DisplayName("When fetching a survey with an invalid id, it should throw an exception")
    public void getSurveyShouldThrowException() {
        Assertions.assertThrows(
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
        surveyService.getQuestions(1L, 1L, 1, 1000);
        verify(surveyRepository).findById(1L);
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
    @DisplayName("When getting questions for a survey with an invalid category id, it should return all factors")
    public void getQuestionsShouldReturnAllFactors() {
        var questionSet = surveyService.getQuestions(1L, 0L, 1, 1000);
        assertEquals(questionSet.getFactors().size(), 1);
    }

    /**
     * createSurvey tests
     */
    @Test
    @DisplayName("When creating a survey, it should be saved in the repository")
    public void createSurveyShouldSave() {
        surveyService.createSurvey(1L, 1L);
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    @DisplayName("When creating a survey with an invalid phase id, it should throw an exception")
    public void createSurveyShouldThrowExceptionPhase() {
        when(interventionService.getPhaseById(2L)).thenThrow(new PhaseNotFoundException(""));
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(2L, 1L)
        );
    }

    @Test
    @DisplayName("When creating a survey with an invalid person id, it should throw an exception")
    public void createSurveyShouldThrowExceptionPerson() {
        when(personService.getPersonById(2L)).thenThrow(new PersonNotFoundException(""));
        assertThrows(
            IllegalArgumentException.class, 
            () -> surveyService.createSurvey(1L, 2L)
        );
    }

    /**
     * submitResponse tests
     */
    @Test
    @DisplayName("When submitting a response, it should be saved in the repository")
    public void submitResponseShouldSave() {    
        SubmitResponseDto responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        Response response = surveyService.submitResponse(SURVEY_ID, responseData);
        assertTrue(survey.getSubfactorById(subfactor.getId()).getResponse().equals(response));
    }

    @Test
    @DisplayName("When submitting a response with an existing response, it should be updated")
    public void submitResponseShouldUpdate() {
        SubmitResponseDto responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        Response response = surveyService.submitResponse(SURVEY_ID, responseData);

        responseData = new SubmitResponseDto(SUBFACTOR_ID, FacilitatingFactor.PENDING, Priority.PENDING, "Comment");
        response = surveyService.submitResponse(SURVEY_ID, responseData);
        assertTrue(survey.getSubfactorById(subfactor.getId()).getResponse().equals(response));
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
    public void createDefaultTemplateShouldReturnActive() {
        surveyService.createDefaultTemplate();
        verify(templateRepository).findFirstByOrderByVersionDesc();
    }

    @Test
    @DisplayName("When creating a default template and none exists, it should create one")
    public void createDefaultTemplateShouldCreate() {
        when(templateRepository.count()).thenReturn(0L);
        surveyService.createDefaultTemplate();
        verify(templateRepository).save(any(Template.class));
    }

    @BeforeEach
    public void setup() {
        this.person = new Person("John", "Doe", "johndoe@gmail.com");
        this.phase = new Phase(PHASE_ID, PhaseName.INITIATION);
        this.intervention = new Intervention("Intervention", "Description", new Person("Admin", "Admin", "admin@gmail.com"));
        this.intervention.addParticipant(person);
        this.intervention.addPhase(phase);

        this.subfactor = new Subfactor(SUBFACTOR_ID, "Subfactor", 1, true);
        this.factor = new Factor(1L, "Factor", 1, List.of(subfactor));
        this.category = new Category(1L, "Category", "", "", List.of(factor));
        this.survey = new Survey(SURVEY_ID, this.phase, List.of(this.category), this.person);

        this.surveyRepository = mock(SurveyRepository.class);
        this.templateRepository = mock(TemplateRepository.class);
        var responseRepository = mock(ResponseRepository.class);
        var categoryRepository = mock(CategoryRepository.class);
        this.interventionService = mock(InterventionService.class);
        this.personService = mock(PersonService.class);
        this.surveyService = new SurveyService(surveyRepository, templateRepository, responseRepository,
                categoryRepository, interventionService, personService);

        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));
        when(interventionService.getPhaseById(1L)).thenReturn(phase);
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));
        when(personService.getPersonById(1L)).thenReturn(person);
    }

    private Template mockTemplate() {
        return mock(Template.class);
    }
}
