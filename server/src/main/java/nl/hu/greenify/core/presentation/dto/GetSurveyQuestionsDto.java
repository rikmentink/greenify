package nl.hu.greenify.core.presentation.dto;

import lombok.Getter;

@Getter
public class GetSurveyQuestionsDto {
    private Long surveyId;
    private Long categoryId;
    private int page;
    private int pageSize;

    public GetSurveyQuestionsDto(Long surveyId, Long categoryId, int page, int pageSize) {
        this.surveyId = surveyId;
        this.categoryId = categoryId;
        this.page = page;
        this.pageSize = pageSize;
    }
}
