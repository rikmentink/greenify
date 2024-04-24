package nl.hu.greenify.core.domain.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.ArrayList;
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

    public double calculateMaximumPossibleScore() {
        double maxFacilitatingFactor = FacilitatingFactor.TOTALLY_AGREE.getValue(true);
        double maxPriority = Priority.TOP_PRIORITY.getValue();
        double maxResponseScore = maxFacilitatingFactor * maxPriority;
        double result = 0;

        result += this.getResponses().stream().mapToDouble(response -> maxResponseScore).sum();

        return result;
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
}
