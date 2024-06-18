package nl.hu.greenify.core.presentation.dto.overview;

import lombok.Getter;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Survey;

@Getter
public class ProgressDto {
    private Long categoryId;
    private double progress;

    protected ProgressDto(Long categoryId, double progress) {
        this.categoryId = categoryId;
        this.progress = progress;
    }

    public static ProgressDto fromEntity(Survey survey, Category category, Person person) {
        return new ProgressDto(
                category.getId(),
                survey.getProgressByCategory(category));
    }
}