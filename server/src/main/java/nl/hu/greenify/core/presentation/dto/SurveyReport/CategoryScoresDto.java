package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class CategoryScoresDto {
    private String categoryName;
    private double maxPossibleScore;
    private double totalScore;
    private double averageScore;

    public static CategoryScoresDto fromEntity(String categoryName, double maxPossibleScore, double totalScore, double averageScore) {
        CategoryScoresDto dto = new CategoryScoresDto();
        dto.categoryName = categoryName;
        dto.maxPossibleScore = maxPossibleScore;
        dto.totalScore = totalScore;
        dto.averageScore = averageScore;
        return dto;
    }
}
