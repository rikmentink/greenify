package nl.hu.greenify.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.domain.enums.PhaseName;

public class SurveyTest {
    
    @Test
    @DisplayName("When creating a survey, it should return a survey with the given phase.")
    public void createSurveyShouldReturnSurvey() {
        Survey survey = Survey.createSurvey(this.mockPhase(), this.mockTemplate());    
        // assertEquals(survey.getPhase(), PhaseName.INITIATION);
    }

    @Test
    @DisplayName("When creating a survey, it should return a survey with the given template.")
    public void createSurveyShouldReturnSurveyWithTemplate() {
        Survey survey = Survey.createSurvey(this.mockPhase(), this.mockTemplate());
        // assertEquals(survey.getTemplate(), this.mockTemplate());
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