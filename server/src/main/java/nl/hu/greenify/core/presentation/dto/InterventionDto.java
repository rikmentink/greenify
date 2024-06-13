package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nl.hu.greenify.core.utils.Calculations.calculateProgress;

@Getter
public class InterventionDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Phase currentPhase;
    private final int surveyAmount;
    private final double totalSurveyProgress;
    private final double phaseProgress;
    private final List<Person> participants;
    private final List<Phase> phases;

    public InterventionDto(Long id, String name, String description, Phase currentPhase, int surveyAmount, double totalSurveyProgress, List<Person> participants, List<Phase> phases, double phaseProgress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentPhase = currentPhase;
        this.surveyAmount = surveyAmount;
        this.totalSurveyProgress = totalSurveyProgress;
        this.participants = participants;
        this.phases = phases;
        this.phaseProgress = phaseProgress;
    }


    public static InterventionDto fromEntity(Intervention intervention, Person person) {
        if(intervention.getCurrentPhase() == null) {
           return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), null, 0, 0, new ArrayList<>(), new ArrayList<>(), 0);
        }

        List<Survey> surveys = intervention.getAllSurveysOfParticipant(person);
        int surveyAmount = surveys.size() + 1;

        double surveyprogress = calculateProgress(surveys);
        double phaseprogress = calculateProgress(intervention.getAllSurveysOfAllPhases());


        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), intervention.getCurrentPhase(), surveyAmount, surveyprogress, intervention.getParticipants(), intervention.getPhases(), phaseprogress);
    }

    public static List<InterventionDto> fromEntities(List<Intervention> interventions, Person person) {
        return interventions.stream().map(intervention -> InterventionDto.fromEntity(intervention, person)).collect(Collectors.toList());
    }
}
