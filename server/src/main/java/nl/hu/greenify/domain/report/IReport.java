package nl.hu.greenify.domain.report;

import java.util.List;

public interface IReport {
    void generateReport();
    double calculateAgreementScore();
    List<String> getActionPoints();
    List<String> getComments();

}
