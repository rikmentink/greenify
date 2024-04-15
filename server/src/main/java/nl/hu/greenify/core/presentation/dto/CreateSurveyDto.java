package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;

@Getter
public class CreateSurveyDto {
    private Long phaseId;

    public CreateSurveyDto(Long phaseId) {
        this.phaseId = phaseId;
    }
}
