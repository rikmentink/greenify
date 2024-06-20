package nl.hu.greenify.core.presentation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.greenify.core.domain.Category;

@Getter
@EqualsAndHashCode
public class QuestionSetDto {
    private Long surveyId;
    private Long phaseId;
    private Long interventionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Category> categories;

    public QuestionSetDto(Long surveyId, Long phaseId, Long interventionId, List<Category> categories) {
        this.surveyId = surveyId;
        this.phaseId = phaseId;
        this.interventionId = interventionId;
        this.categories = categories;
    }

    public static QuestionSetDto fromEntity(Long surveyId, Long phaseId, Long interventionId, List<Category> categories) {
        return new QuestionSetDto(surveyId, phaseId, interventionId, categories);
    }
}
