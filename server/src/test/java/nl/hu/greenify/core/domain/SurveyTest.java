package nl.hu.greenify.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.greenify.core.application.exceptions.SubfactorNotFoundException;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

public class SurveyTest {
    private Survey survey;
    private Category category;
    private Factor factor;
    private Subfactor subfactor;
    private Person person;
    
    @Test
    @DisplayName("When creating a survey, it should return a survey with the given phase.")
    public void createSurveyShouldReturnSurvey() {
        Phase phase = this.mockPhase();
        Survey survey = Survey.createSurvey(phase, this.mockTemplate(), person);    
        assertEquals(survey.getPhase(), phase);
    }

    @Test
    @DisplayName("When creating a survey, it should return a survey with the given template.")
    public void createSurveyShouldReturnSurveyWithTemplate() {
        Template template = this.mockTemplate();
        Survey survey = Survey.createSurvey(this.mockPhase(), this.mockTemplate(), person);
        assertEquals(survey.getCategories(), template.getCategories());
    }

    @Test
    @DisplayName("When creating a survey with an invalid phase, it should throw an exception.")
    public void createSurveyShouldThrowException() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> Survey.createSurvey(null, this.mockTemplate(), person)
        );
    }

    @Test
    @DisplayName("When creating a survey with an invalid template, it should throw an exception.")
    public void createSurveyShouldThrowExceptionWithInvalidTemplate() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> Survey.createSurvey(this.mockPhase(), null, person)
        );
    }

    @Test
    @DisplayName("When creating a survey with an invalid respondent, it should throw an exception.")
    public void createSurveyShouldThrowExceptionWithInvalidRespondent() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> Survey.createSurvey(this.mockPhase(), this.mockTemplate(), null)
        );
    }

    @Test
    @DisplayName("When saving a response, it should add the response to the subfactor.")
    public void saveResponseShouldAddResponseToSubfactor() {
        var subfactorId = 1L;

        Response response = this.survey.saveResponse(subfactorId, FacilitatingFactor.PENDING, Priority.PENDING, "");
        assertEquals(subfactor.getResponse(), response);
    }

    @Test
    @DisplayName("When saving a response with an invalid subfactor ID, it should throw an exception.")
    public void saveResponseShouldThrowException() {
        assertThrows(
            SubfactorNotFoundException.class, 
            () -> this.survey.saveResponse(2L, FacilitatingFactor.PENDING, Priority.PENDING, "")
        );
    }
    
    @BeforeEach
    void setup() {
        this.person = new Person("John", "Doe", "you@example.com");
        this.survey = new Survey(1L, new Phase(PhaseName.INITIATION), new ArrayList<>(), this.person);
        this.category = new Category(1L, "", "", "", new ArrayList<>());
        this.factor = new Factor(1L, "", 0, new ArrayList<>());
        this.subfactor = new Subfactor(1L, "subfactor", 1, true);
        this.factor.addSubfactor(subfactor);
        this.category.addFactor(factor);
        this.survey.addCategory(category);
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