package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static nl.hu.greenify.core.utils.Calculations.calculateProgress;

@Getter
public class InterventionDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Phase currentPhase;
    private final int surveyAmount;
    private final double progress;

    public InterventionDto(Long id, String name, String description, Phase currentPhase, int surveyAmount, double progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentPhase = currentPhase;
        this.surveyAmount = surveyAmount;
        this.progress = progress;
    }


    public static InterventionDto fromEntity(Intervention intervention, Person person) {
        if(intervention.getCurrentPhase() == null) {
           return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), null, 0, 0);
        }

        List<Survey> surveys = intervention.getAllSurveysOfParticipant(person);
        int surveyAmount = surveys.size();

        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), intervention.getCurrentPhase(), surveyAmount, calculateProgress(surveys));
    }

    public static List<InterventionDto> fromEntities(List<Intervention> interventions, Person person) {
        return interventions.stream().map(intervention -> InterventionDto.fromEntity(intervention, person)).collect(Collectors.toList());
    }
}
