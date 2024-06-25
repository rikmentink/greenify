package nl.hu.greenify.core.presentation.dto.overview;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;

@Getter
public class PhaseProgressDto {
    private Long id;
    private String name;
    private String description;
    private Long surveyId;
    private List<ParticipantDto> contenders;
    private List<CategoryDto> categories;

    public PhaseProgressDto(Long id, String name, String description, Long surveyId, List<ParticipantDto> contenders,
            List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.surveyId = surveyId;
        this.contenders = contenders;
        this.categories = categories;
    }

    public static PhaseProgressDto fromEntities(Intervention intervention, Phase phase, List<Person> participants, Person currentUser) {
        if (phase.getSurveys().isEmpty())
            throw new IllegalArgumentException("Phase should have at least one survey");
        
        Survey surveyOfCurrentUser = phase.getSurveyOfPerson(currentUser).orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        return new PhaseProgressDto(
                phase.getId(),
                phase.getName().getValue(),
                phase.getDescription(),
                surveyOfCurrentUser.getId(),
                ParticipantDto.fromEntities(phase, participants, currentUser),
                CategoryDto.fromEntities(surveyOfCurrentUser));
    }

    public static PhaseProgressDto fromEntity(Intervention intervention, Phase phase, Person participant) {
        if (phase.getSurveys().isEmpty())
            throw new IllegalArgumentException("Phase should have at least one survey");

        Survey surveyOfCurrentUser = phase.getSurveyOfPerson(participant).orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        return new PhaseProgressDto(
                phase.getId(),
                phase.getName().getValue(),
                phase.getDescription(),
                surveyOfCurrentUser.getId(),
                Arrays.asList(ParticipantDto.fromEntity(phase, participant, true)),
                CategoryDto.fromEntities(surveyOfCurrentUser));
    }
}
