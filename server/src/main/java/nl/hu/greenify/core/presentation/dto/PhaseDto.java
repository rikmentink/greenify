package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;

import java.util.List;

import static nl.hu.greenify.core.utils.Calculations.calculateProgress;

@Getter
public class PhaseDto {
    private final Long id;
    private final String name;
    private final List<Survey> surveys;
    private final double progress;

    public PhaseDto(Long id, String name, List<Survey> surveys, double progress) {
        this.id = id;
        this.name = name;
        this.surveys = surveys;
        this.progress = progress;
    }

    public static PhaseDto fromEntity(Phase phase) {
        return new PhaseDto(phase.getId(), phase.getName().toString(), phase.getSurveys(), calculateProgress(phase.getSurveys()));
    }

    public static List<PhaseDto> fromEntities(List<Phase> phases) {
        return phases.stream().map(PhaseDto::fromEntity).toList();
    }
}
