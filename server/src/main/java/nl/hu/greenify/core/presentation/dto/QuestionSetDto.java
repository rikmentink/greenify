package nl.hu.greenify.core.presentation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Survey;

@Getter
@EqualsAndHashCode
public class QuestionSetDto {
    private Long interventionId;
    private Long phaseId;
    private Long surveyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Category> categories;

    public QuestionSetDto(Long interventionId, Long phaseId, Long surveyId, List<Category> categories) {
        this.interventionId = interventionId;
        this.phaseId = phaseId;
        this.surveyId = surveyId;
        this.categories = categories;
    }

    public static QuestionSetDto fromEntity(Survey survey, List<Category> categories) {
        Long phaseId = survey.getPhaseId();
        Long interventionId = survey.getPhase().getInterventionId();
        return new QuestionSetDto(interventionId, phaseId, survey.getId(), categories);
    }
}
