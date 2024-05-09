package nl.hu.greenify.core.domain.report;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveyReportTest {
    private Phase phase;
    private Subfactor subfactor1Survey1;
    private Subfactor subfactor2Survey1;
    private Subfactor subfactor3Survey1;
    private Subfactor subfactor4Survey1;
    private Subfactor subfactor1Survey2;
    private Subfactor subfactor2Survey2;
    private Subfactor subfactor3Survey2;
    private Subfactor subfactor4Survey2;

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

    @Test
    @DisplayName("When responses are provided, the maximum possible agreement score should be calculated.")
    void testMaxScore() {
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        SurveyReport surveyReport = new SurveyReport(phase);

        assertEquals(20.0, surveyReport.getMaxScore());
    }

    @Test
    @DisplayName("When no responses are provided (yet), the maximum possible agreement score should be 0.0.")
    void testMaximumPossibleAgreementScoreWithNoResponses() {
        SurveyReport surveyReport = new SurveyReport(phase);

        assertEquals(0.0, surveyReport.getMaxScore());
    }

    @Test
    @DisplayName("When responses are given to subfactors across multiple categories, MAXIMUM scores can be given per provided category.")
    void testMaxScorePerCategorySingleSurvey() {
        // Survey 1:
        // Part of category 1
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        // Part of category 2
        Response response3 = new Response(this.subfactor3Survey1);
        response3.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response3.setPriority(Priority.TOP_PRIORITY);
        this.subfactor3Survey1.setResponse(response3);

        SurveyReport surveyReport = new SurveyReport(phase);

        // Get the first category of the first survey
        Category category = phase.getSurveys().get(0).getCategories().get(0);
        Category category2 = phase.getSurveys().get(0).getCategories().get(1);

        assertAll(
                () -> assertEquals(20.0, surveyReport.getMaxScoreOfCategory(category)),
                () -> assertEquals(10.0, surveyReport.getMaxScoreOfCategory(category2))
        );
    }

    @Test
    @DisplayName("When responses are given to subfactors across multiple categories within a SINGLE survey, AVERAGE scores can be given per provided category.")
    void testAverageScorePerCategorySingleSurvey() {
        // Survey 1:
        // Part of category 1
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        // Part of category 2
        Response response3 = new Response(this.subfactor3Survey1);
        response3.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response3.setPriority(Priority.TOP_PRIORITY);
        this.subfactor3Survey1.setResponse(response3);

        SurveyReport surveyReport = new SurveyReport(phase);

        // Get the first category of the first survey
        Category category = phase.getSurveys().get(0).getCategories().get(0);
        Category category2 = phase.getSurveys().get(0).getCategories().get(1);

        assertAll(
                () -> assertEquals(6.33, surveyReport.getAverageScore(category)),
                () -> assertEquals(2.0, surveyReport.getAverageScore(category2))
        );
    }

    @Test
    @DisplayName("When responses are given to subfactors across multiple categories over MULTIPLE survey, AVERAGE scores can be given per provided category name.")
    void testAverageScorePerCategoryMultipleSurveys() {
        // Survey 1:
        // Part of category 1
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        // Part of category 2
        Response response3 = new Response(this.subfactor3Survey1);
        response3.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response3.setPriority(Priority.TOP_PRIORITY);
        this.subfactor3Survey1.setResponse(response3);

        // Survey 2:
        // Part of category 1
        Response response4 = new Response(this.subfactor1Survey2);
        response4.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response4.setPriority(Priority.NO_PRIORITY);
        this.subfactor1Survey2.setResponse(response4);

        // Part of category 2
        Response response5 = new Response(this.subfactor3Survey2);
        response5.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response5.setPriority(Priority.NO_PRIORITY);
        this.subfactor3Survey2.setResponse(response5);

        SurveyReport surveyReport = new SurveyReport(phase);

        // Expected average scores:
        // Category "name": (6.33 + 2) / 2 = 4.165
        // Category "name2": (2 + 1) / 2 = 1.5

        assertAll(
                () -> assertEquals(4.165, surveyReport.getAverageScoreOfCategoryByName("name")),
                () -> assertEquals(1.5, surveyReport.getAverageScoreOfCategoryByName("name2"))
        );
    }

    @Test
    @DisplayName("When responses are given to subfactors across multiple categories over MULTIPLE survey, AVERAGE scores can be given for a subfactor per provided factor number and subfactor number.")
    void testAverageScorePerSubfactorMultipleSurveys() {
        // Subfactor 1 and 2 fall under factor 1 (both supporting factors)
        // Subfactor 3 and 4 fall under factor 2 (Both non-supporting factors)

        // Survey 1:
        // Part of category 1
        Response response = new Response(this.subfactor1Survey1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1Survey1.setResponse(response);

        Response response2 = new Response(this.subfactor2Survey1);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2Survey1.setResponse(response2);

        // Part of category 2
        Response response3 = new Response(this.subfactor3Survey1);
        response3.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response3.setPriority(Priority.TOP_PRIORITY);
        this.subfactor3Survey1.setResponse(response3);

}

