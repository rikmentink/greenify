package nl.hu.greenify.core.domain.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity
public class SurveyReport implements IReport {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @OneToOne
    private Phase phase;

    @ElementCollection
    private List<String> comments;

    public SurveyReport(Phase phase) {
        this.phase = phase;
    }

    protected SurveyReport() {
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

        result += this.getResponses().stream().mapToDouble(response -> maxResponseScore).sum();

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
    public List<Response> getResponses() {
        List<Response> responses = phase.getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream())
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

    public double getAverageScore(Category category) {
        List<Response> responses = getResponsesOfCategory(category);
        double totalScore = responses.stream().mapToDouble(Response::getScore).sum();
        return totalScore / responses.size();
    }

    /**
     * This method is used to return the average score of a category by its name. The name is
     * used to identify the category to allow for an average score to be calculated across
     * multiple surveys.
     *
     * @return The average score of a category across all surveys in the phase.
     */
    public double getAverageScoreOfCategoryByName(String categoryName) {
        List<Category> categories = phase.getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream())
                .filter(category -> category.getName().equals(categoryName))
                .toList();

        double totalScore = 0;
        for (Category category : categories) {
            totalScore += getAverageScore(category);
        }

        return totalScore / categories.size();
    }

    public double getAverageScoreOfSubfactor(int factorNumber, int subfactorNumber) {
        List<Response> responses = phase.getSurveys().stream()
                .flatMap(survey -> survey.getCategories().stream())
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
}
