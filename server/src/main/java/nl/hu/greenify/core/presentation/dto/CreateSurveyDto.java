package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;

@Getter
public class CreateSurveyDto {
    private Long phaseId;
    private Long personId;

    protected CreateSurveyDto() {
    }

    public CreateSurveyDto(Long phaseId, Long personId) {
        this.phaseId = phaseId;
        this.personId = personId;
    }

    public String toJsonString() {
        return "{\"phaseId\":" + phaseId + ",\"personId\":" + personId + "}";
    }
}
