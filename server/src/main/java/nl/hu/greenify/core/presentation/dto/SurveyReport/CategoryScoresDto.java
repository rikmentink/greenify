package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class CategoryScoresDto {
    private String categoryName;
    private double maxScore;
    private double averageScore;

    public static CategoryScoresDto fromEntity(String categoryName, double maxScore, double averageScore) {
        CategoryScoresDto dto = new CategoryScoresDto();
        dto.categoryName = categoryName;
        dto.maxScore = maxScore;
        dto.averageScore = averageScore;
        return dto;
    }
}
