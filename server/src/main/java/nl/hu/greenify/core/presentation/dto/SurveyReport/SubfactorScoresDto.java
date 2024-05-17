package nl.hu.greenify.core.presentation.dto.SurveyReport;

import lombok.Getter;

@Getter
public class SubfactorScoresDto {
    private String subfactorName;
    private double maxScore;
    private double averageScore;

    public static SubfactorScoresDto fromEntity(String subfactorName, double maxScore, double averageScore) {
        SubfactorScoresDto dto = new SubfactorScoresDto();
        dto.subfactorName = subfactorName;
        dto.maxScore = maxScore;
        dto.averageScore = averageScore;
        return dto;
    }
}
