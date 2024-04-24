package nl.hu.greenify.core.application;

import java.util.List;

import org.springframework.stereotype.Service;

import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.application.exceptions.TemplateNotFoundException;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;
import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.Template;
import nl.hu.greenify.core.presentation.dto.QuestionSetDto;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final TemplateRepository templateRepository;
    private final InterventionService interventionService;

    public SurveyService(SurveyRepository surveyRepository, TemplateRepository templateRepository,
            InterventionService interventionService) {
        this.surveyRepository = surveyRepository;
        this.templateRepository = templateRepository;
        this.interventionService = interventionService;
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Survey with ID " + id + " not found."));
        return survey;
    }

    /**
     * Creates a new survey based on the given phase.
     *
     * @param phaseId The ID of the phase to create the survey for.
     * @return The created survey.
     */
    public Survey createSurvey(Long phaseId) {
        this.createTemplateIfNotExists();
        Phase phase = interventionService.getPhaseById(phaseId);

        Survey survey = Survey.createSurvey(phase, this.getActiveTemplate());
        return surveyRepository.save(survey);
    }

    /**
     * Get the questions for the given survey and category.
     * TODO: Keep page and page size into account.
     * 
     * @param surveyId   The ID of the survey to get the questions for.
     * @param categoryId The ID of the category to get the questions for.
     * @return The questions for the given survey and category.
     */
    public QuestionSetDto getQuestions(Long surveyId, Long categoryId) {
        Survey survey = this.getSurvey(surveyId);
        return QuestionSetDto.fromEntity(survey, categoryId);
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

    private void createTemplateIfNotExists() {
        if (this.templateRepository.count() > 0) return;
        this.templateRepository.save(
            this.generateBasicTemplate()
        );
    }

    private Template generateBasicTemplate() {
        return Template.createTemplate(
            "Default Template",
            "Should be changed for production!",
            1,
            List.of(
                new Category(
                    1L,
                    "Domein 1",
                    "#FF0000",
                    "This is the first domain of the default template."
                ),
                new Category(
                    1L,
                    "Domein 1",
                    "#FF0000",
                    "This is the first domain of the default template."
                )
            )
        );
    }
}
