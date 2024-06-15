package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class CategoryScoresDto {
    private String categoryName;
    private double maxPossibleScore;
    private double totalScore;
    private double percentage;

    public static CategoryScoresDto fromEntity(String categoryName, double maxPossibleScore, double totalScore) {
        CategoryScoresDto dto = new CategoryScoresDto();
        dto.categoryName = categoryName;
        dto.maxPossibleScore = maxPossibleScore;
        dto.totalScore = totalScore;
        // Translate the total score to a percentage for better readability
        dto.percentage = (totalScore * 100) / maxPossibleScore;
        return dto;
    }
}
