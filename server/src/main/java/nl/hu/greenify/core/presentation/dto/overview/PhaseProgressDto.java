package nl.hu.greenify.core.presentation.dto.overview;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;

@Getter
public class PhaseProgressDto {
    private Long id;
    private String name;
    private List<ParticipantDto> contenders;
    private List<CategoryDto> categories;

    public PhaseProgressDto(Long id, String name, List<ParticipantDto> contenders, List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.contenders = contenders;
        this.categories = categories;
    }

    public static PhaseProgressDto fromEntities(Intervention intervention, Phase phase, List<Person> participants, Person currentUser) {
        if (phase.getSurveys().isEmpty())
            throw new IllegalArgumentException("Phase should have at least one survey");

        return new PhaseProgressDto(
                phase.getId(),
                phase.getName().getValue(),
                ParticipantDto.fromEntities(phase, participants, currentUser),
                CategoryDto.fromEntities(phase.getSurveys().get(0)));
    }

    public static PhaseProgressDto fromEntity(Intervention intervention, Phase phase, Person participant) {
        if (phase.getSurveys().isEmpty())
            throw new IllegalArgumentException("Phase should have at least one survey");

        return new PhaseProgressDto(
                phase.getId(),
                phase.getName().getValue(),
                Arrays.asList(ParticipantDto.fromEntity(phase, participant, true)),
                CategoryDto.fromEntities(phase.getSurveys().get(0)));
    }
}
