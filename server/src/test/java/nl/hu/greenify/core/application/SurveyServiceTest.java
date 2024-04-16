package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.Template;

public class SurveyServiceTest {
    private SurveyService surveyService;
    private SurveyRepository surveyRepository;
    private TemplateRepository templateRepository;
    private InterventionService interventionService;

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

    @BeforeEach
    public void setup() {
        this.surveyRepository = mock(SurveyRepository.class);
        this.templateRepository = mock(TemplateRepository.class);
        this.interventionService = mock(InterventionService.class);
        this.surveyService = new SurveyService(surveyRepository, templateRepository, interventionService);
    
        when(surveyRepository.findById(1L)).thenReturn(Optional.of(this.getSurveyExample()));
        when(interventionService.getPhaseById(1L)).thenReturn(new Phase(PhaseName.INITIATION));
        when(templateRepository.findFirstByOrderByVersionDesc()).thenReturn(Optional.of(this.mockTemplate()));
    }

    private Template mockTemplate() {
        Template template = mock(Template.class);
        return template;
    }

    private Survey getSurveyExample() {
        // TODO: Update the given phase?
        return new Survey(1L, "Survey", "Description", 1, new ArrayList<>(), new Phase(PhaseName.INITIATION));
    }
}
