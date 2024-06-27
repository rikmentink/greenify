package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.SurveyReportService;
import nl.hu.greenify.core.presentation.dto.SurveyReport.CategoryScoresDto;
import nl.hu.greenify.core.presentation.dto.SurveyReport.SubfactorScoresDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/survey-report")
public class SurveyReportController extends Controller {
    private final SurveyReportService surveyReportService;

    public SurveyReportController(SurveyReportService surveyReportService) {
        this.surveyReportService = surveyReportService;
    }

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{phaseId}/category-scores", produces="application/json")
    public ResponseEntity<?> getAverageScoresOfCategoryForPhase(@PathVariable("phaseId") Long phaseId) {
        List<CategoryScoresDto> categoryScores = this.surveyReportService.getCategoryScores(phaseId);
        return this.createResponse(categoryScores);
    }

//    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{phaseId}/subfactor-scores/{categoryName}", produces="application/json")
    public ResponseEntity<?> getAverageScoreOfSubfactorsInCategoryForPhase(@PathVariable("phaseId") Long phaseId, @PathVariable("categoryName") String categoryName) {
        List<SubfactorScoresDto> subfactorScores = this.surveyReportService.getSubfactorScores(phaseId, categoryName);
        return this.createResponse(subfactorScores);
    }
}
