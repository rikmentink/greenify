package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.presentation.dto.CreateSurveyDto;
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
import nl.hu.greenify.core.domain.Survey;

@RestController
@RequestMapping("/survey")
public class SurveyController extends Controller {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(
        produces = "application/json"
    )
    public ResponseEntity<?> getAllSurveys() {
        List<Survey> surveys = this.surveyService.getAllSurveys();
        return this.createResponse(surveys.stream().map(SurveyDto::fromEntity));
    }

    @GetMapping(
        value = "/{id}", 
        produces = "application/json"
    )
    public ResponseEntity<?> getSurvey(@PathVariable String id) {
        Survey survey = this.surveyService.getSurvey(Long.parseLong(id));
        return this.createResponse(SurveyDto.fromEntity(survey));
    }

    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<?> createSurvey(@RequestBody CreateSurveyDto createSurveyDto) {
        Survey survey = this.surveyService.createSurvey(createSurveyDto.getPhaseId());
        return this.createResponse(SurveyDto.fromEntity(survey), HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/{id}/questions",
        produces = "application/json"
    )
    public ResponseEntity<?> getSurveyQuestions(@PathVariable String id, @RequestParam Long categoryId,
            @RequestParam int page, @RequestParam int pageSize) {
        QuestionSetDto questions = this.surveyService.getQuestions(id, categoryId);
        return this.createResponse(questions);
    }
}