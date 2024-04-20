package nl.hu.greenify.core.domain.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Response> getResponses() {
        // TODO: Remove prints once done
        List<Response> responses = new ArrayList<>();

        System.out.println("SURVEYS:");
        System.out.println(phase.getSurveys());
        for (Survey survey : phase.getSurveys()) {

            System.out.println("CATEGORIES:");
            System.out.println(survey.getCategories());
            for (Category category : survey.getCategories()) {

                System.out.println("FACTORS:");
                System.out.println(category.getFactors());
                for (Factor factor : category.getFactors()) {

                    System.out.println("SUBFACTORS:");
                    System.out.println(factor.getSubfactors());
                    for (Subfactor subfactor : factor.getSubfactors()) {

                        System.out.println("RESPONSE:");
                        System.out.println(subfactor.getResponse());
                        if (subfactor.getResponse() != null) {
                            responses.add(subfactor.getResponse());
                        }
                    }
                }
            }
        }
        return responses;
    }
}
