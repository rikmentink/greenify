package nl.hu.greenify.core.application;

import org.springframework.stereotype.Service;

import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.exceptions.SurveyNotFoundException;
import jakarta.transaction.Transactional;

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
