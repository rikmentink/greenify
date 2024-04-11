package nl.hu.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.SurveyService;
import data.SurveyRepository;
import domain.Survey;
import exceptions.SurveyNotFoundException;

public class SurveyServiceTests {
    private SurveyService surveyService;
    private SurveyRepository surveyRepository;
    private Survey survey = this.getSurveyExample();
    
    @Test
    @DisplayName("When fetching a survey with a valid id, it should be fetched from the repository")
    public void getSurveyShouldFetch() {
        surveyService.getSurvey(1L);
        verify(surveyRepository).findById(1L);
    }

    @Test
    @DisplayName("When fetching a survey with an invalid id, it should throw an exception")
    public void getSurveyShouldThrowException() {
        assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.getSurvey(2L)
        );
    }

    @BeforeEach
    public void setup() {
        this.surveyRepository = mock(SurveyRepository.class);
        when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));

        this.surveyService = new SurveyService(surveyRepository);
    }

    private Survey getSurveyExample() {
        return new Survey(1L, "Survey", "Description", 0, new ArrayList<>());
    }
}
