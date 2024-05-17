package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SurveyReportServiceTest {
    private Phase phase;
    private Subfactor subfactor1Survey1;
    private Subfactor subfactor2Survey1;
    private Subfactor subfactor3Survey1;
    private Subfactor subfactor4Survey1;
    private Subfactor subfactor1Survey2;
    private Subfactor subfactor2Survey2;
    private Subfactor subfactor3Survey2;
    private Subfactor subfactor4Survey2;

    private final InterventionService interventionService = mock(InterventionService.class);
    private final SurveyReportService surveyReportService = new SurveyReportService(interventionService);

    @BeforeEach
    void setUp() {
        // Template creation steps:
        // 1. Create categories
        Category categoryTemplate1 = new Category(1L, "name", "red", "description", new ArrayList<>());
        Category categoryTemplate2 = new Category(2L, "name2", "blue", "description2", new ArrayList<>());

        // 2. Create factors
        Factor factorTemplate1 = new Factor(1L, "title", 1, new ArrayList<>());
        Factor factorTemplate2 = new Factor(2L, "title2", 2, new ArrayList<>());

        // 3. Attach each factor to a category
        categoryTemplate1.addFactor(factorTemplate1);
        categoryTemplate2.addFactor(factorTemplate2);

        // 4. Create subfactors
        Subfactor subfactorTemplate1 = new Subfactor(1L, "title", 1, true);
        Subfactor subfactorTemplate2 = new Subfactor(2L, "title2", 2, true);
        Subfactor subfactorTemplate3 = new Subfactor(3L, "title3", 3, false);
        Subfactor subfactorTemplate4 = new Subfactor(4L, "title4", 4, false);

        // 5. Attach each subfactor to a factor
        factorTemplate1.addSubfactor(subfactorTemplate1);
        factorTemplate1.addSubfactor(subfactorTemplate2);
        factorTemplate2.addSubfactor(subfactorTemplate3);
        factorTemplate2.addSubfactor(subfactorTemplate4);

        // 6. Create a template with the categories
        Template template = new Template(1L, "name", "description", 1, List.of(categoryTemplate1, categoryTemplate2));

        // Phase creation to create surveys for based on the template:
        this.phase = new Phase(PhaseName.INITIATION);

        // Person creation to set as the respondant:
        Person person = new Person("John", "Doe", "you@example.com");

        // Survey creations based on templates:
        Survey.createSurvey(phase, Template.copyOf(template), person);
        Survey.createSurvey(phase, Template.copyOf(template), person);

        // Prepare subfactors that can be used to provide responses on
        setupProvideResponseSurvey1();
        setupProvideResponseSurvey2();

        // Actually provide responses on the subfactors
        setupResponses();

        // Mock the intervention service to return the phase
        when(interventionService.getPhaseById(1L)).thenReturn(this.phase);
    }

    // Helper methods to prepare for responses for the surveys
    void setupProvideResponseSurvey1() {
        Survey survey = phase.getSurveys().get(0);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor1Survey1 = factor.getSubfactors().get(0);
        this.subfactor2Survey1 = factor.getSubfactors().get(1);

        Category category2 = survey.getCategories().get(1);
        Factor factor2 = category2.getFactors().get(0);
        this.subfactor3Survey1 = factor2.getSubfactors().get(0);
        this.subfactor4Survey1 = factor2.getSubfactors().get(1);
    }

    void setupProvideResponseSurvey2() {
        Survey survey = phase.getSurveys().get(1);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor1Survey2 = factor.getSubfactors().get(0);
        this.subfactor2Survey2 = factor.getSubfactors().get(1);

        Category category2 = survey.getCategories().get(1);
        Factor factor2 = category2.getFactors().get(0);
        this.subfactor3Survey2 = factor2.getSubfactors().get(0);
        this.subfactor4Survey2 = factor2.getSubfactors().get(1);
    }

    void setupResponses() {
        Response response1 = Response.createResponse(subfactor1Survey1, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response response2 = Response.createResponse(subfactor2Survey1, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);
        Response response3 = Response.createResponse(subfactor3Survey1, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response response4 = Response.createResponse(subfactor4Survey1, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);
        Response response5 = Response.createResponse(subfactor1Survey2, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response response6 = Response.createResponse(subfactor2Survey2, FacilitatingFactor.AGREE, Priority.LITTLE_PRIORITY, null);
        Response response7 = Response.createResponse(subfactor3Survey2, FacilitatingFactor.TOTALLY_AGREE, Priority.LITTLE_PRIORITY, null);
        Response response8 = Response.createResponse(subfactor4Survey2, FacilitatingFactor.DISAGREE, Priority.TOP_PRIORITY, null);
    }

    @Test
    @DisplayName("Test average score of a category across multiple surveys in a phase")
    public void testAverageScoreOfCategoryForPhase() {
        // When:
        double result = surveyReportService.getAverageScoreOfCategoryForPhase(1L,  "name");

        // Then:
        // Survey 1 scores: 10 + 2.66
        // Survey 2 scores: 10 + 5.32
        // Total: 27.98
        // Average: 27.98 / 4 = 6.995
        assertEquals(6.995, result);
    }

    @Test
    @DisplayName("Test obtaining averages of ALL categories within a phase across multiple surveys")
    public void testAverageScoresOfAllCategoriesForPhase() {
        // Given:
        Map<String, Double> expectedScores = new HashMap<>();
        expectedScores.put("name", 6.995);
        expectedScores.put("name2", 4.1625);

        // When:
        Map<String, Double> actualScores = surveyReportService.getAverageScoresOfAllCategoriesForPhase(1L);

        // Then:
        assertEquals(expectedScores, actualScores);
    }

    @Test
    @DisplayName("Test obtaining averages of ALL subfactors in a category within a phase across multiple surveys")
    public void testAverageScoresOfEachSubfactorInCategory() {
        // Given:
        Map<String, Double> expectedScores = new HashMap<>();
        expectedScores.put("title", 10.0);
        expectedScores.put("title2", 3.99);

        // When:
        Map<String, Double> actualScores = surveyReportService.getAverageScoresOfEachSubfactorInCategory(1L, "name");

        // Then:
        assertEquals(expectedScores, actualScores);
    }
}
