package nl.hu.greenify.core.presentation.dto;

import nl.hu.greenify.core.domain.Survey;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class SurveyDto {
    private Long id;
    private String phaseName;
    private String personName;

    public SurveyDto(Long id, String phaseName, String personName) {
        this.id = id;
        this.phaseName = phaseName;
        this.personName = personName;
    }

    public static SurveyDto fromEntity(Survey survey) {
        return new SurveyDto(survey.getId(), survey.getPhase().getName().toString(), survey.getRespondent().getFullName());
    }

    public static List<SurveyDto> fromEntities(List<Survey> surveys) {
        return surveys.stream().map(SurveyDto::fromEntity).collect(Collectors.toList());
    }
}
