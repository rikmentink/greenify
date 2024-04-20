package nl.hu.greenify.core.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.IFactor;
import nl.hu.greenify.core.domain.factor.Subfactor;

@EqualsAndHashCode
public class QuestionSetDto {
    private Long surveyId;
    private Long categoryId;
    private List<Factor> factors;
    private List<Subfactor> subfactors;

    public QuestionSetDto(Long surveyId, Long categoryId, List<Factor> factors,
            List<Subfactor> subfactors) {
        this.surveyId = surveyId;
        this.categoryId = categoryId;
        this.factors = factors;
        this.subfactors = subfactors;
    }

    public static QuestionSetDto fromEntity(Survey survey, Long categoryId) {
        Category category = survey.getCategories().stream().filter(c -> c.getId().equals(categoryId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        List<Subfactor> subfactors = category.getFactors().stream()
                .flatMap(f -> f.getSubfactors().stream())
                .collect(Collectors.toList());
        return new QuestionSetDto(survey.getId(), categoryId, category.getFactors(), subfactors);
    }
}
