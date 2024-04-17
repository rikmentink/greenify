package nl.hu.greenify.core.presentation.dto;

import nl.hu.greenify.core.domain.Survey;
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
}
