package nl.hu.greenify.core.domain.report;

import lombok.Setter;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.interfaces.Report;

import java.util.List;

public class SurveyReport implements Report {
    private Long id;

    @Setter
    private Phase phase;
    private List<String> comments;

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
}