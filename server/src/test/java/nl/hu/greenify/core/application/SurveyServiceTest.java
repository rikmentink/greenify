package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;

public class SurveyServiceTest {
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
     * createSurvey tests
     */
    @Test
    @DisplayName("When creating a survey, it should be saved in the repository")
    public void createSurveyShouldSave() {
        surveyService.createSurvey(1L);
        verify(surveyRepository).save(any(Survey.class));
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

    @BeforeEach
    public void setup() {
        this.person = new Person("John", "Doe", "johndoe@gmail.com");
        this.intervention = new Intervention("Intervention", "Description", new Person("Admin", "Admin", "admin@gmail.com"));
        this.intervention.addParticipant(person);
        this.intervention.addPhase(PhaseName.INITIATION);

        this.surveyRepository = mock(SurveyRepository.class);
        this.templateRepository = mock(TemplateRepository.class);
        this.interventionService = mock(InterventionService.class);
        this.personService = mock(PersonService.class);
        this.surveyService = new SurveyService(surveyRepository, templateRepository, interventionService, personService);

        when(surveyRepository.findById(1L)).thenReturn(Optional.of(this.getSurveyExample()));
        when(interventionService.getPhaseById(1L)).thenReturn(new Phase(PhaseName.INITIATION));
        when(interventionService.getPhaseById(2L)).thenReturn(new Phase(PhaseName.EXECUTION));
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));
        when(personService.getPersonById(1L)).thenReturn(person);
    }

    private Template mockTemplate() {
        return mock(Template.class);
    }

    private Survey getSurveyExample() {
        return new Survey(1L, "Survey", "Description", 1, List.of(new Category(1L, "Category", "", "", new ArrayList<>())), new Phase(PhaseName.INITIATION));
    }
}
