package nl.hu.greenify.core.domain.report;

import java.lang.reflect.Array;
import java.util.List;

public interface IReport {
    void generateReport();
    double calculateAgreementScore();
    List<String> getActionPoints();
    List<String> getComments();

}
