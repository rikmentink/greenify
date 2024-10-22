package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.presentation.dto.QuestionSetDto;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;
import nl.hu.greenify.core.presentation.dto.SurveyDto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import nl.hu.greenify.core.application.SurveyService;
import nl.hu.greenify.core.domain.Response;

@RestController
@RequestMapping("/survey")
public class SurveyController extends Controller {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Secured("ROLE_VUMEDEWERKER")
    @GetMapping(produces="application/json")
    public ResponseEntity<?> getAllSurveys() {
        List<SurveyDto> surveys = SurveyDto.fromEntities(this.surveyService.getAllSurveys());
        return this.createResponse(surveys);
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getSurvey(@PathVariable("id") Long id) {
        SurveyDto survey = SurveyDto.fromEntity(this.surveyService.getSurvey(id));
        return this.createResponse(survey);
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @GetMapping(value="/{id}/questions", produces="application/json")
    public ResponseEntity<?> getSurveyQuestions(
            @PathVariable("id") Long id,
            @RequestParam(required=false) Long categoryId,
            @RequestParam(required=false) int page, 
            @RequestParam(required=false) int pageSize) {
        QuestionSetDto questions = this.surveyService.getQuestions(id, categoryId, page, pageSize);
        return this.createResponse(questions);
    }

    @Secured("ROLE_VUMEDEWERKER")
    @PostMapping(value="/template/default", produces="application/json")
    public ResponseEntity<?> createDefaultTemplate() throws Exception {
        return this.createResponse(this.surveyService.createDefaultTemplate());
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @PostMapping(value="{id}/response", consumes="application/json", produces="application/json")
    public ResponseEntity<?> submitResponse(@PathVariable("id") Long id, @RequestBody SubmitResponseDto responseDto) {
        Response response = this.surveyService.submitResponse(id, responseDto);
        return this.createResponse(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_MANAGER", "ROLE_USER", "ROLE_VUMEDEWERKER"})
    @DeleteMapping(value="{surveyId}/response/{subfactorId}", produces="application/json")
    public ResponseEntity<?> deleteResponse(@PathVariable("surveyId") Long surveyId, @PathVariable("subfactorId") Long subfactorId) {
        this.surveyService.deleteResponse(surveyId, subfactorId);
        return this.createResponse(null);
    }
}