package nl.hu.greenify.application;

import org.springframework.stereotype.Service;

import data.SurveyRepository;
import domain.Survey;
import jakarta.transaction.Transactional;
import nl.hu.greenify.exceptions.SurveyNotFoundException;

@Service
@Transactional
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey getSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Survey with ID " + id + " not found."));
        return survey;
    }
}
