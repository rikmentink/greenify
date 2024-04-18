package nl.hu.greenify.core.presentation.dto;

import java.util.List;

import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.factor.IFactor;

public class QuestionSetDto {
    private Long surveyId;
    private Long categoryId;
    private int page;
    private int pageSize;
    private List<IFactor> factors;
    private List<IFactor> subfactors;

    public QuestionSetDto(Long surveyId, Long categoryId, int page, int pageSize, List<IFactor> factors,
            List<IFactor> subfactors) {
        this.surveyId = surveyId;
        this.categoryId = categoryId;
        this.page = page;
        this.pageSize = pageSize;
        this.factors = factors;
        this.subfactors = subfactors;
    }

    public static QuestionSetDto fromEntity(Survey survey, List<IFactor> factors, List<IFactor> subfactors) {
        return new QuestionSetDto(survey.getId(), survey.getCategories().get(0).getId(), 0, 0, factors, subfactors);
    }
}
