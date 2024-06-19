package nl.hu.greenify.core.presentation.dto.overview;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;

@Getter
public class ParticipantDto {
    private Long id;
    private String name;
    private List<ProgressDto> progress;
    private List<ResponseDto> responses;
    private boolean isCurrentUser;

    public ParticipantDto(Long id, String name, List<ProgressDto> progress, List<ResponseDto> responses,
            boolean isCurrentUser) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.responses = responses;
        this.isCurrentUser = isCurrentUser;
    }

    public static List<ParticipantDto> fromEntities(Phase phase, List<Person> participants, Person currentUser) {
        return participants.stream()
                .map(person -> fromEntity(phase, person, person.equals(currentUser)))
                .collect(Collectors.toList());
    }

    public static ParticipantDto fromEntity(Phase phase, Person participant, boolean isCurrentUser) {
        Survey survey = phase.getSurveyOfPerson(participant)
                .orElseThrow(() -> new IllegalArgumentException("Participant has no survey in this phase"));
        return new ParticipantDto(
                participant.getId(),
                participant.getFullName(),
                survey.getCategories().stream()
                        .map(category -> ProgressDto.fromEntity(survey, category, participant))
                        .collect(Collectors.toList()),
                survey.getAllSubfactors().stream()
                        .filter(subfactor -> subfactor.getResponse() != null)
                        .map(subfactor -> ResponseDto.fromEntity(subfactor))
                        .collect(Collectors.toList()),
                isCurrentUser);
    }
}
