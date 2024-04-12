package nl.hu.greenify.domain.report;

import lombok.Setter;
import nl.hu.greenify.domain.Phase;
import nl.hu.greenify.domain.interfaces.Report;

import java.util.List;

public class ResearchReport implements Report {
    private Long id;

    @Setter
    private Phase phase;
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
