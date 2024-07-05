package nl.hu.greenify.core.domain.report;

import lombok.Getter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class SurveyReport implements IReport {
    private final Phase phase;

    private List<String> comments;

    public SurveyReport(Phase phase) {
        this.phase = phase;
    }

    @Override
    public List<String> getActionPoints() {
        return null;
    }

    @Override
    public List<String> getComments() {
        return null;
    }

    public double getMaxScoreOfCategory(Category category) {
        double maxResponseScore = calculateMaxResponseScore();
        double result = 0;

        result += category.getFactors().stream()
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getResponse() != null) // Only consider subfactors with a response! TODO: confirm if this should also be done for "PENDING" responses.
                .mapToDouble(subfactor -> maxResponseScore)
                .sum();

        return result;
    }

    private double calculateMaxResponseScore() {
        double maxFacilitatingFactor = FacilitatingFactor.TOTALLY_AGREE.getValue(true);
        double maxPriority = Priority.TOP_PRIORITY.getValue();
        return maxFacilitatingFactor * maxPriority;
    }

    public double calculateAverageScoreOfSubfactor(int factorNumber, int subfactorNumber) {
        List<Response> responses = this.getAllCategories()
                .flatMap(category -> category.getFactors().stream())
                .filter(factor -> factor.getNumber() == factorNumber)
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getNumber() == subfactorNumber)
                .map(Subfactor::getResponse)
                .filter(Objects::nonNull)
                .toList();

        if (responses.isEmpty()) {
            return 0;
        }

        double totalScore = responses.stream().mapToDouble(Response::getScore).sum();
        return totalScore / responses.size();
    }

    public Map<String, Double> calculateMaxPossibleScoresOfAllCategories() {
        Map<String, Double> categoryScores = new HashMap<>();
        Map<String, List<Category>> groupedCategories = this.getAllCategories()
                .collect(Collectors.groupingBy(Category::getName));

        groupedCategories
                .forEach((name, categories) -> {
                    double maxScore = categories.stream()
                            .mapToDouble(this::getMaxScoreOfCategory)
                            .sum();
                    categoryScores.put(name, maxScore);
                });

        return categoryScores;
    }

    public Map<String, Double> calculateMaxPossibleScoresOfEachSubfactorInCategory(String categoryName) {
        Map<String, Double> subfactorScores = new HashMap<>();

        this.getAllCategories()
                .filter(category -> category.getName().equals(categoryName))
                .flatMap(category -> category.getFactors().stream())
                .forEach(factor -> factor.getSubfactors()
                        .forEach(subfactor -> {
                            double maxScore = this.calculateMaxResponseScore();
                            subfactorScores.put(subfactor.getTitle(), maxScore);
                        }));

        return subfactorScores;
    }

    public Map<String, Double> calculateAverageScoresOfEachSubfactorInCategory(String categoryName) {
        Map<String, Double> subfactorScores = new HashMap<>();

        this.getAllCategories()
                .filter(category -> category.getName().equals(categoryName))
                .flatMap(category -> category.getFactors().stream())
                .forEach(factor -> factor.getSubfactors()
                        .forEach(subfactor -> {
                            double averageScore = this.calculateAverageScoreOfSubfactor(factor.getNumber(), subfactor.getNumber());
                            subfactorScores.put(subfactor.getTitle(), averageScore);
                        }));

        return subfactorScores;
    }

    private Stream<Category> getAllCategories() {
        return this.getPhase().getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream());
    }

    public Map<String, Double> calculateTotalScoresOfAllCategories() {
        Map<String, Double> categoryScores = new HashMap<>();
        Map<String, List<Category>> groupedCategories = this.getAllCategories()
                .collect(Collectors.groupingBy(Category::getName));

        groupedCategories
                .forEach((name, categories) -> {
                    double totalScore = categories.stream()
                            .mapToDouble(this::getTotalScoreOfCategory)
                            .sum();
                    categoryScores.put(name, totalScore);
                });

        return categoryScores;
    }

    private double getTotalScoreOfCategory(Category category) {
        double result = 0;

        result += category.getFactors().stream()
                .flatMap(factor -> factor.getSubfactors().stream())
                .filter(subfactor -> subfactor.getResponse() != null) // Only consider subfactors with a response! TODO: confirm if this should also be done for "PENDING" responses.
                .mapToDouble(subfactor -> subfactor.getResponse().getScore())
                .sum();

        return result;
    }
}
