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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SurveyReportTest {

    private Category category;
    private Factor factor;
    private Subfactor subfactor;
    private Template template;
    private Phase phase;
    private Subfactor subfactor1;
    private Subfactor subfactor2;

    @BeforeEach
    void setUp() {
        // Template creation steps:
        this.category = new Category(1L, "name", "red", "description");
        this.factor = new Factor(1L, "title", 1);
        category.addFactor(factor);
        this.subfactor = new Subfactor(1L, "title", 1, true);
        factor.addSubfactor(subfactor);
        this.template = new Template(1L, "name", "description", 1, List.of(category));

        this.phase = new Phase(PhaseName.INITIATION);

        // Survey creations based on templates:
        Survey.createSurvey(phase, Template.copyOf(template));
        Survey.createSurvey(phase, Template.copyOf(template));
        System.out.println(phase.getSurveys());

    }

    void setupProvideResponseSurvey1() {
        Survey survey = phase.getSurveys().get(0);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor1 = factor.getSubfactors().get(0);

    }

    void setupProvideResponseSurvey2() {
        Survey survey = phase.getSurveys().get(1);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor2 = factor.getSubfactors().get(0);

    }
    @Test
    @DisplayName("When responses are provided, the maximum possible agreement score should be calculated.")
    void testMaximumPossibleAgreementScore() {
        // Survey 1:
        setupProvideResponseSurvey1();
        Response response = new Response(this.subfactor1);
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        this.subfactor1.setResponse(response);

        // Survey 2:
        setupProvideResponseSurvey2();
        Response response2 = new Response(this.subfactor2);
        response2.setFacilitatingFactor(FacilitatingFactor.DISAGREE);
        response2.setPriority(Priority.LITTLE_PRIORITY);
        subfactor2.setResponse(response2);

        SurveyReport surveyReport = new SurveyReport(phase);

        assertEquals(20.0, surveyReport.calculateMaximumPossibleScore());
    }

    @Test
    @DisplayName("When no responses are provided (yet), the maximum possible agreement score should be 0.0.")
    void testMaximumPossibleAgreementScoreWithNoResponses() {
        SurveyReport surveyReport = new SurveyReport(phase);

        assertEquals(0.0, surveyReport.calculateMaximumPossibleScore());
    }
}

