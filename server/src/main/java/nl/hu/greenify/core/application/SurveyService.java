package nl.hu.greenify.core.application;

import org.springframework.stereotype.Service;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.application.exceptions.TemplateNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.Template;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final TemplateRepository templateRepository;

    public SurveyService(SurveyRepository surveyRepository, TemplateRepository templateRepository) {
        this.surveyRepository = surveyRepository;
        this.templateRepository = templateRepository;
    }

    public Survey getSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Survey with ID " + id + " not found."));
        return survey;
    }

    /**
     * Creates a new survey based on the given phase.
     *
     * @param phase The phase for which the survey is created.
     * @return The created survey.
     */
    public Survey createSurvey(Phase phase) {
        Survey survey = Survey.createSurvey(phase, this.getActiveTemplate());
        surveyRepository.save(survey);
        return survey;
    }

    /**
     * Retrieve the active template by fetching the latest version.
     * 
     * @return The latest version of the template.
     */
    private Template getActiveTemplate() {
        return this.templateRepository.findFirstByOrderByVersionDesc()
                .orElseThrow(() -> new TemplateNotFoundException("No active template found."));
    }
}
