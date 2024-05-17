package nl.hu.greenify.core.presentation.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Survey;
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

    public static QuestionSetDto fromEntity(Survey survey, Long categoryId) {
        Category category = survey.getCategories().stream().filter(c -> c.getId().equals(categoryId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return new QuestionSetDto(survey.getId(), category, category.getFactors());
    }
}
