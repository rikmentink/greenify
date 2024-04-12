package nl.hu.greenify.domain.interfaces;

import java.lang.reflect.Array;
import java.util.List;

public interface Report {
    void generateReport();
    double calculateAgreementScore();
    List<String> getActionPoints();
    List<String> getComments();

}
