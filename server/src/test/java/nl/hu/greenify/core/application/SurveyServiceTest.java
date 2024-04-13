package nl.hu.greenify.core.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;

import org.mockito.Mockito;

public class SurveyServiceTest {
    private SurveyService surveyService;
    private SurveyRepository surveyRepository;
    private Survey survey = this.getSurveyExample();
    
    @Test
    @DisplayName("When fetching a survey with a valid id, it should be fetched from the repository")
    public void getSurveyShouldFetch() {
        surveyService.getSurvey(1L);
        Mockito.verify(surveyRepository).findById(1L);
    }

    @Test
    @DisplayName("When fetching a survey with an invalid id, it should throw an exception")
    public void getSurveyShouldThrowException() {
        Assertions.assertThrows(
            SurveyNotFoundException.class, 
            () -> surveyService.getSurvey(2L)
        );
    }

    @BeforeEach
    public void setup() {
        this.surveyRepository = Mockito.mock(SurveyRepository.class);
        Mockito.when(surveyRepository.findById(1L)).thenReturn(Optional.of(survey));

        this.surveyService = new SurveyService(surveyRepository);
    }

    private Survey getSurveyExample() {
        return new Survey(1L, "Survey", "Description", 0, new ArrayList<>());
    }
}
