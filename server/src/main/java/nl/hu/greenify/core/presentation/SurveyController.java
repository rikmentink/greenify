package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.presentation.dto.CreateSurveyDto;
import nl.hu.greenify.core.presentation.dto.QuestionSetDto;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;
import nl.hu.greenify.core.presentation.dto.SurveyDto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.hu.greenify.core.application.SurveyService;
import nl.hu.greenify.core.domain.Response;

@RestController
@RequestMapping("/survey")
public class SurveyController extends Controller {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(produces="application/json")
    public ResponseEntity<?> getAllSurveys() {
        List<SurveyDto> surveys = SurveyDto.fromEntities(this.surveyService.getAllSurveys());
        return this.createResponse(surveys);
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getSurvey(@PathVariable("id") Long id) {
        SurveyDto survey = SurveyDto.fromEntity(this.surveyService.getSurvey(id));
        return this.createResponse(survey);
    }

    @GetMapping(value="/{id}/questions", produces="application/json")
    public ResponseEntity<?> getSurveyQuestions(
            @PathVariable("id") Long id,
            @RequestParam(required=false) Long categoryId,
            @RequestParam(required=false) int page, 
            @RequestParam(required=false) int pageSize) {
        QuestionSetDto questions = this.surveyService.getQuestions(id, categoryId, page, pageSize);
        return this.createResponse(questions);
    }

    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<?> createSurvey(@RequestBody CreateSurveyDto createSurveyDto) {
        SurveyDto survey = SurveyDto.fromEntity(
                this.surveyService.createSurvey(createSurveyDto.getPhaseId(), createSurveyDto.getPersonId()));
        return this.createResponse(survey, HttpStatus.CREATED);
    }

    @PostMapping(value="/template/default", produces="application/json")
    public ResponseEntity<?> createDefaultTemplate() {
        return this.createResponse(this.surveyService.createDefaultTemplate());
    }

    @PostMapping(value="{id}/response", consumes="application/json", produces="application/json")
    public ResponseEntity<?> submitResponse(@PathVariable("id") Long id, @RequestBody SubmitResponseDto responseDto) {
        Response response = this.surveyService.submitResponse(id, responseDto);
        return this.createResponse(response, HttpStatus.CREATED);
    }
}