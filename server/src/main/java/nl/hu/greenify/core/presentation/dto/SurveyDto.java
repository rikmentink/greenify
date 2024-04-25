package nl.hu.greenify.core.presentation.dto;

import nl.hu.greenify.core.domain.Survey;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class SurveyDto {
    private Long id;
    private String name;
    private String description;
    private String phase;

    public SurveyDto(Long id, String name, String description, String phase) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phase = phase;
    }

    public static SurveyDto fromEntity(Survey survey) {
        return new SurveyDto(survey.getId(), survey.getName(), survey.getDescription(), survey.getPhase().getName().toString());
    }

    public static List<SurveyDto> fromEntities(List<Survey> surveys) {
        return surveys.stream().map(SurveyDto::fromEntity).collect(Collectors.toList());
    }
}
