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
    public void generateReport() {

    }

    @Override
    public double calculateAgreementScore() {
        return 0;
    }

    @Override
    public List<String> getActionPoints() {
        return null;
    }

    @Override
    public List<String> getComments() {
        return null;
    }

    public double getMaxScore() {
        double maxResponseScore = calculateMaxResponseScore();
        double result = 0;

        result += this.getAllResponses().stream().mapToDouble(response -> maxResponseScore).sum();

        return result;
    }

    public double getMaxScoreOfCategory(Category category) {
        double maxResponseScore = calculateMaxResponseScore();
        double result = 0;

        result += this.getResponsesOfCategory(category).stream().mapToDouble(response -> maxResponseScore).sum();

        return result;
    }

    private double calculateMaxResponseScore() {
        double maxFacilitatingFactor = FacilitatingFactor.TOTALLY_AGREE.getValue(true);
        double maxPriority = Priority.TOP_PRIORITY.getValue();
        return maxFacilitatingFactor * maxPriority;
    }

    @Override
    public List<Response> getAllResponses() {
        List<Response> responses = this.getAllCategories()
                .flatMap(category -> category.getFactors().stream())
                .flatMap(factor -> factor.getSubfactors().stream())
                .map(Subfactor::getResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return responses;
    }

    public List<Response> getResponsesOfCategory(Category category) {
        List<Response> responses = category.getFactors().stream()
                .flatMap(factor -> factor.getSubfactors().stream())
                .map(Subfactor::getResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return responses;
    }

    /**
     * This method is used to return the average score of a category by its name. The name is
     * used to identify the category to allow for an average score to be calculated across
     * multiple surveys.
     *
     * @return The average score of a category across all surveys in the phase.
     */
    public double calculateAverageScoreOfCategory(String categoryName) {
        List<Category> categories = this.getAllCategories()
                .filter(category -> category.getName().equals(categoryName))
                .toList();

        double totalScore = this.getAllCategories()
                .filter(category -> category.getName().equals(categoryName))
                .mapToDouble(this::getAverageScore)
                .sum();

        return totalScore / categories.size();
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

        double totalScore = responses.stream().mapToDouble(Response::getScore).sum();
        return totalScore / responses.size();
    }

    public Map<String, Double> calculateAverageScoresOfAllCategories() {
        Map<String, Double> categoryScores = new HashMap<>();

        this.getAllCategories()
                .forEach(category -> {
                    double averageScore = this.calculateAverageScoreOfCategory(category.getName());
                    categoryScores.put(category.getName(), averageScore);
                });

        return categoryScores;
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

    public double getAverageScore(Category category) {
        List<Response> responses = getResponsesOfCategory(category);
        double totalScore = responses.stream().mapToDouble(Response::getScore).sum();
        return totalScore / responses.size();
    }



    private Stream<Category> getAllCategories() {
        return this.getPhase().getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream());
    }
}
