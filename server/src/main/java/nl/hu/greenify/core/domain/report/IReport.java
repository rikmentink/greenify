package nl.hu.greenify.core.domain.report;

import nl.hu.greenify.core.domain.Response;

import java.util.List;

public interface IReport {
    List<String> getActionPoints();
    List<String> getComments();
}
