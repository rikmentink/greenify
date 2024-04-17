package nl.hu.greenify.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.domain.enums.PhaseName;

public class SurveyTest {
    
    @Test
    @DisplayName("When creating a survey, it should return a survey with the given phase.")
    public void createSurveyShouldReturnSurvey() {
        Phase phase = this.mockPhase();
        Survey survey = Survey.createSurvey(phase, this.mockTemplate());    
        assertEquals(survey.getPhase(), phase);
    }

    @Test
    @DisplayName("When creating a survey, it should return a survey with the given template.")
    public void createSurveyShouldReturnSurveyWithTemplate() {
        Template template = this.mockTemplate();
        Survey survey = Survey.createSurvey(this.mockPhase(), this.mockTemplate());
        assertEquals(survey.getCategories(), template.getCategories());
    }

    @Test
    @DisplayName("When creating a survey with an invalid phase, it should throw an exception.")
    public void createSurveyShouldThrowException() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> Survey.createSurvey(null, this.mockTemplate())
        );
    }

    @Test
    @DisplayName("When creating a survey with an invalid template, it should throw an exception.")
    public void createSurveyShouldThrowExceptionWithInvalidTemplate() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> Survey.createSurvey(this.mockPhase(), null)
        );
    }

    private Phase mockPhase() {
        Phase phase = mock(Phase.class);
        when(phase.getName()).thenReturn(PhaseName.INITIATION);
        return phase;
    }

    private Template mockTemplate() {
        Template template = mock(Template.class);
        when(template.getVersion()).thenReturn(1);
        when(template.getCategories()).thenReturn(new ArrayList<>());
        return template;
    }
}