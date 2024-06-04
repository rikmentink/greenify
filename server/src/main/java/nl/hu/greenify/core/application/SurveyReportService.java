package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.report.SurveyReport;
import nl.hu.greenify.core.presentation.dto.SurveyReport.CategoryScoresDto;
import nl.hu.greenify.core.presentation.dto.SurveyReport.SubfactorScoresDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    private Map<String, Double> getTotalScoresOfAllCategoriesForPhase(Long phaseId) {
        SurveyReport surveyReport = getSurveyReportByPhaseId(phaseId);
        return surveyReport.calculateTotalScoresOfAllCategories();
    }

    public List<CategoryScoresDto> getCategoryScores(Long phaseId) {
        Map<String, Double> maxPossibleScores = this.getMaxPossibleScoresOfAllCategoriesForPhase(phaseId);
        Map<String, Double> totalScores = this.getTotalScoresOfAllCategoriesForPhase(phaseId);
        Map<String, Double> averageScores = this.getAverageScoresOfAllCategoriesForPhase(phaseId);

        // Convert the maps to a list of CategoryScoresDto objects, matching each category name with its max and average score.
        return maxPossibleScores.entrySet().stream()
                .map(entry -> {
                    String categoryName = entry.getKey();
                    double maxScore = entry.getValue();
                    double totalScore = totalScores.get(categoryName);
                    double averageScore = averageScores.get(categoryName);
                    return CategoryScoresDto.fromEntity(categoryName, maxScore, totalScore, averageScore);
                })
                .collect(Collectors.toList());
    }

    public List<SubfactorScoresDto> getSubfactorScores(Long phaseId, String categoryName) {
        Map<String, Double> maxScores = this.getMaxPossibleScoresOfEachSubfactorInCategory(phaseId, categoryName);
        Map<String, Double> averageScores = this.getAverageScoresOfEachSubfactorInCategory(phaseId, categoryName);

        // Convert the maps to a list of SubfactorScoresDto objects, matching each subfactor name with its max and average score.
        return maxScores.entrySet().stream()
                .map(entry -> {
                    String subfactorName = entry.getKey();
                    double maxScore = entry.getValue();
                    double averageScore = averageScores.get(subfactorName);
                    return SubfactorScoresDto.fromEntity(subfactorName, maxScore, averageScore);
                })
                .collect(Collectors.toList());
    }
}
