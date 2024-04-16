package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.presentation.dto.CreateSurveyDto;
import nl.hu.greenify.core.presentation.dto.SurveyDto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.hu.greenify.core.application.SurveyService;
import nl.hu.greenify.core.domain.Survey;

@RestController
@RequestMapping("/survey")
public class SurveyController {
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
    public ResponseEntity<?> getSurvey(@PathVariable Long id) {
        Survey survey = this.surveyService.getSurvey(id);
        return this.createResponse(SurveyDto.fromEntity(survey));
    }

    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    public ResponseEntity<?> createSurvey(CreateSurveyDto createSurveyDto) {
        Survey survey = this.surveyService.createSurvey(createSurveyDto.getPhaseId());
        return this.createResponse(SurveyDto.fromEntity(survey), HttpStatus.CREATED);
    }

    /**
     * Helper to create proper response entities.
     * 
     * @param body The response body to return
     * @param status The status of the response
     * @return A response entity with the given body and status
     */
    private ResponseEntity<?> createResponse(Object body, HttpStatus status) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    /**
     * Helper to create proper response entities with status OK.
     * 
     * @param body The response body to return
     * @return A response entity with the given body and status OK
     */
    private ResponseEntity<?> createResponse(Object body) {
        return this.createResponse(body, HttpStatus.OK);
    }
}
