package nl.hu.greenify.core.domain.report;

import lombok.Setter;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Response;

import java.util.List;

public class ResearchReport implements IReport {
    private Long id;

    @Setter
    private Phase phase;

    @Override
    public List<String> getActionPoints() {
        return null;
    }

    @Override
    public List<String> getComments() {
        return null;
    }
}
