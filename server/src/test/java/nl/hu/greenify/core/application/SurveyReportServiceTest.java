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

        // Survey creations based on templates:
        Survey.createSurvey(phase, Template.copyOf(template));
        Survey.createSurvey(phase, Template.copyOf(template));

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
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        Response response3 = new Response(this.subfactor3Survey1);
        response3.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response3.setPriority(Priority.TOP_PRIORITY);
        subfactor3Survey1.setResponse(response3);

        Response response4 = new Response(this.subfactor4Survey1);
        response4.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response4.setPriority(Priority.LITTLE_PRIORITY);
        subfactor4Survey1.setResponse(response4);

        Response response5 = new Response(this.subfactor1Survey2);
        response5.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response5.setPriority(Priority.TOP_PRIORITY);
        subfactor1Survey2.setResponse(response5);

        Response response6 = new Response(this.subfactor2Survey2);
        response6.setFacilitatingFactor(FacilitatingFactor.AGREE);
        response6.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey2.setResponse(response6);

        Response response7 = new Response(this.subfactor3Survey2);
        response7.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response7.setPriority(Priority.LITTLE_PRIORITY);
        subfactor3Survey2.setResponse(response7);

        Response response8 = new Response(this.subfactor4Survey2);
        response8.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response8.setPriority(Priority.TOP_PRIORITY);
        subfactor4Survey2.setResponse(response8);
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
