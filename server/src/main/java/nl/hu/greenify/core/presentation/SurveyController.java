package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.presentation.dto.SurveyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurvey(Long id) {
        Survey survey = this.surveyService.getSurvey(id);
        return new ResponseEntity<SurveyDto>(SurveyDto.fromEntity(survey), HttpStatus.OK);
    }
}
