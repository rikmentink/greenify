package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class SubfactorScoresDto {
    private String subfactorName;
    private double maxPossibleScore;
    private double averageScore;

    public static SubfactorScoresDto fromEntity(String subfactorName, double maxPossibleScore, double averageScore) {
        SubfactorScoresDto dto = new SubfactorScoresDto();
        dto.subfactorName = subfactorName;
        dto.maxPossibleScore = maxPossibleScore;
        dto.averageScore = averageScore;
        return dto;
    }
}
