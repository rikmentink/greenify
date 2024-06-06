package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InterventionDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Phase currentPhase;
    private final int surveyAmount;

    public InterventionDto(Long id, String name, String description, Phase currentPhase, int surveyAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentPhase = currentPhase;
        this.surveyAmount = surveyAmount;//NOTE: getSurveys() change to SurveysOfPerson
    }


    public static InterventionDto fromEntity(Intervention intervention, Person person) {
        if(intervention.getCurrentPhase() == null) {
           return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), null, 0);
        }

        int surveyAmount = intervention.getAllSurveysOfParticipant(person).size();

        return new InterventionDto(intervention.getId(), intervention.getName(), intervention.getDescription(), intervention.getCurrentPhase(), surveyAmount); //NOTE: Should be surveys of person, not all surveys
    }

    public static List<InterventionDto> fromEntities(List<Intervention> interventions, Person person) {
        return interventions.stream().map(intervention -> InterventionDto.fromEntity(intervention, person)).collect(Collectors.toList());
    }
}
