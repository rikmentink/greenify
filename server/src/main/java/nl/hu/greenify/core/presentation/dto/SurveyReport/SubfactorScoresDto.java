package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class SubfactorScoresDto {
    private String subfactorName;
    private double maxPossibleScore;
    private double averageScore;
    double percentage;

    public static SubfactorScoresDto fromEntity(String subfactorName, double maxPossibleScore, double averageScore) {
        SubfactorScoresDto dto = new SubfactorScoresDto();
        dto.subfactorName = subfactorName;
        dto.maxPossibleScore = maxPossibleScore;
        dto.averageScore = averageScore;
        // Translate the average score to a percentage for better readability
        dto.percentage = (averageScore * 100) / maxPossibleScore;
        return dto;
    }
}
