package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.report.SurveyReport;

import java.util.Map;

public class SurveyReportService {

    private final InterventionService interventionService;

    public SurveyReportService(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    private SurveyReport getSurveyReportByPhaseId(Long phaseId) {
        Phase phase = interventionService.getPhaseById(phaseId);
        return new SurveyReport(phase);
    }

    public double getAverageScoreOfSubfactorForPhase(Long phaseId, int factorNumber, int subfactorNumber) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateAverageScoreOfSubfactor(factorNumber, subfactorNumber);
    }

    public double getAverageScoreOfCategoryForPhase(Long phaseId, String categoryName) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateAverageScoreOfCategory(categoryName);
    }

    public Map<String, Double> getAverageScoresOfEachSubfactorInCategory(Long phaseId, String categoryName) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateAverageScoresOfEachSubfactorInCategory(categoryName);
    }

    public Map<String, Double> getMaxPossibleScoresOfEachSubfactorInCategory(Long phaseId, String categoryName) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateMaxPossibleScoresOfEachSubfactorInCategory(categoryName);
    }

    public Map<String, Double> getAverageScoresOfAllCategoriesForPhase(Long phaseId) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateAverageScoresOfAllCategories();
    }

    public Map<String, Double> getMaxPossibleScoresOfAllCategoriesForPhase(Long phaseId) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateMaxPossibleScoresOfAllCategories();
    }
}
