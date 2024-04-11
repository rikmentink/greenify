package presentation.dto;

import domain.Survey;
import lombok.Getter;

@Getter
public class SurveyDto {
    private Long id;
    private String name;
    private String description;

    public SurveyDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static SurveyDto fromEntity(Survey survey) {
        return new SurveyDto(survey.getId(), survey.getName(), survey.getDescription());
    }
}
