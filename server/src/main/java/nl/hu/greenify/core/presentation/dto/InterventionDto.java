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
    private final List<Person> participants;
    private final List<Double> participantProgress;
    private final List<Phase> phases;

    public InterventionDto(Long id, String name, String description, Phase currentPhase, int surveyAmount, double totalSurveyProgress, List<Person> participants, List<Double> participantProgress, List<Phase> phases) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentPhase = currentPhase;
        this.surveyAmount = surveyAmount;
        this.totalSurveyProgress = totalSurveyProgress;
        this.participants = participants;
        this.participantProgress = participantProgress;
        this.phases = phases;
    }


    public static InterventionDto fromEntity(Intervention intervention, Person person) {
        List<Survey> surveys = intervention.getSurveysOfPersonInCurrentPhase(person);
        double surveyProgress = calculateProgress(surveys);
        List<Double> participantProgress = new ArrayList<>();

        if(intervention.getCurrentPhase() == null) {
            return createEmptyInterventionDto(intervention);
        }

        for(Person person2 : intervention.getParticipants()) {
                participantProgress.add(calculateParticipantProgress(person2, intervention));
            }

        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), intervention.getCurrentPhase(), surveys.size() + 1, surveyProgress, intervention.getParticipants(), participantProgress, intervention.getPhases());
    }

    public static List<InterventionDto> fromEntities(List<Intervention> interventions, Person person) {
        return interventions.stream().map(intervention -> InterventionDto.fromEntity(intervention, person)).collect(Collectors.toList());
    }

    private static InterventionDto createEmptyInterventionDto(Intervention intervention) {
        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), null, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private static double calculateParticipantProgress(Person person, Intervention intervention) {
        double participantProgress = 0;

        for(Survey survey : person.getSurveys()) {
            if(survey.getPhase().equals(intervention.getCurrentPhase())) {
                participantProgress += calculateProgress(List.of(survey));
            }
        }

        return participantProgress;
    }
}
