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
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;

public class SurveyServiceTest {
    private static final Long SURVEY_ID = 1L;
    private static final Long SUBFACTOR_ID = 1L;

    private Subfactor subfactor;
    private Response response;
    private Factor factor;
    private Category category;
    private Survey survey;

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
        surveyService.getQuestions(1L, 1L);
        verify(surveyRepository).findById(1L);
    }

    @Test
    @DisplayName("When getting questions for a survey with an invalid id, it should throw an exception")
    public void getQuestionsShouldThrowException() {
        assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.getQuestions(2L, 1L)
        );
    }

    @Test
    @DisplayName("A Survey should be added to a person")
    public void addSurveyToPerson() {
        surveyService.addSurveyToPerson(1L, 1L);
        verify(personService).getPersonById(1L);
    }

    @Test
    @DisplayName("The same survey should not be added to a person twice")
    public void addSurveyToPersonTwice() {
        surveyService.addSurveyToPerson(1L, 1L);
        person.setSurveyId(1L);
        assertThrows(
            IllegalArgumentException.class,
            () -> surveyService.addSurveyToPerson(1L, 1L)
        );
    }

    @Test
    @DisplayName("A survey should only be added twice if the phases are different")
    public void addSurveyToPersonTwiceDifferentPhase() {
        surveyService.addSurveyToPerson(1L, 1L);
        surveyService.addSurveyToPerson(1L, 2L);
    }

    /**
     * createSurvey tests
     */
    @Test
    @DisplayName("When creating a survey, it should be saved in the repository")
    public void createSurveyShouldSave() {
        surveyService.createSurvey(1L);
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    @DisplayName("When submitting a response, it should be saved in the repository")
    public void submitResponseShouldSave() {    
        this.response = new Response(subfactor);    
        surveyService.submitResponse(SURVEY_ID, response);
        assertTrue(survey.getSubfactorById(subfactor.getId()).getResponse().equals(response));
    }

    @Test
    @DisplayName("When submitting a response with an existing response, it should be updated")
    public void submitResponseShouldUpdate() {
        this.response = new Response(subfactor);
        surveyService.submitResponse(SURVEY_ID, response);

        Response newResponse = new Response(subfactor);
        surveyService.submitResponse(SURVEY_ID, newResponse);
        assertTrue(survey.getSubfactorById(subfactor.getId()).getResponse().equals(newResponse));
    }

    @Test
    @DisplayName("When submitting a response with an invalid survey id, it should throw an exception")
    public void submitResponseShouldThrowException() {
        assertThrows(
            SurveyNotFoundException.class,
            () -> surveyService.submitResponse(2L, new Response(subfactor))
        );
    }

    @BeforeEach
    public void setup() {
        this.person = new Person("John", "Doe", "johndoe@gmail.com");
        this.intervention = new Intervention("Intervention", "Description", new Person("Admin", "Admin", "admin@gmail.com"));
        this.intervention.addParticipant(person);
        this.intervention.addPhase(PhaseName.INITIATION);

        this.subfactor = new Subfactor(SUBFACTOR_ID, "Subfactor", 1, true);
        this.factor = new Factor(1L, "Factor", 1, List.of(subfactor));
        this.category = new Category(1L, "Category", "", "", List.of(factor));
        this.survey = new Survey(SURVEY_ID, "Survey", "Description", 1, List.of(this.category), new Phase(PhaseName.INITIATION));

        this.surveyRepository = mock(SurveyRepository.class);
        this.templateRepository = mock(TemplateRepository.class);
        this.interventionService = mock(InterventionService.class);
        this.personService = mock(PersonService.class);
        this.surveyService = new SurveyService(surveyRepository, templateRepository, interventionService, personService);

        when(surveyRepository.findById(SURVEY_ID)).thenReturn(Optional.of(this.survey));
        when(interventionService.getPhaseById(1L)).thenReturn(new Phase(PhaseName.INITIATION));
        when(interventionService.getPhaseById(2L)).thenReturn(new Phase(PhaseName.EXECUTION));
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));
        when(personService.getPersonById(1L)).thenReturn(person);
    }

    private Template mockTemplate() {
        return mock(Template.class);
    }
}
