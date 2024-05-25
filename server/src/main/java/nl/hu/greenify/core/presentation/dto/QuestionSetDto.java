package nl.hu.greenify.core.presentation.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.factor.Factor;

@Getter
@EqualsAndHashCode
public class QuestionSetDto {
    private Long surveyId;
    private Category category;
    private List<Factor> factors;

    public QuestionSetDto(Long surveyId, Category category, List<Factor> factors) {
        this.surveyId = surveyId;
        this.category = category;
        this.factors = factors;
    }

    public static QuestionSetDto fromEntity(Long surveyId, Category category, List<Factor> factors) {
        return new QuestionSetDto(surveyId, category, factors);
    }
}
