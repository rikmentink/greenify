package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.report.SurveyReport;

import java.util.HashMap;
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
        return surveyReport.getAverageScoreOfSubfactor(factorNumber, subfactorNumber);
    }

    public double getAverageScoreOfCategoryForPhase(Long phaseId, String categoryName) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.getAverageScoreOfCategoryByName(categoryName);
    }

    public Map<String, Double> getAverageScoresOfEachSubfactorInCategory(Long phaseId, String categoryName) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        Map<String, Double> subfactorScores = new HashMap<>();

        surveyReport.getPhase().getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream())
                .filter(category -> category.getName().equals(categoryName))
                .flatMap(category -> category.getFactors().stream())
                .forEach(factor -> factor.getSubfactors()
                        .forEach(subfactor -> {
                    double averageScore = surveyReport.getAverageScoreOfSubfactor(factor.getNumber(), subfactor.getNumber());
                    subfactorScores.put(subfactor.getTitle(), averageScore);
                }));

        return subfactorScores;
    }

    public Map<String, Double> getAverageScoresOfAllCategoriesForPhase(Long phaseId) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        Map<String, Double> categoryScores = new HashMap<>();

        surveyReport.getPhase().getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream())
                .forEach(category -> {
                    double averageScore = surveyReport.getAverageScoreOfCategoryByName(category.getName());
                    categoryScores.put(category.getName(), averageScore);
                });

        return categoryScores;
    }
}