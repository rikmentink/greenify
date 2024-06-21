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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Category> categories;

    public QuestionSetDto(Long surveyId, List<Category> categories) {
        this.surveyId = surveyId;
        this.categories = categories;
    }

    public static QuestionSetDto fromEntity(Long surveyId, List<Category> categories) {
        return new QuestionSetDto(surveyId, categories);
    }
}
