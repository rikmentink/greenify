package nl.hu.greenify.core.application;

import java.util.List;

import nl.hu.greenify.core.domain.*;
import org.springframework.stereotype.Service;

import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.application.exceptions.PhaseNotFoundException;
import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.application.exceptions.TemplateNotFoundException;
import nl.hu.greenify.core.data.CategoryRepository;
import nl.hu.greenify.core.data.ResponseRepository;
import nl.hu.greenify.core.data.SurveyRepository;
import nl.hu.greenify.core.data.TemplateRepository;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import nl.hu.greenify.core.presentation.dto.QuestionSetDto;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final TemplateRepository templateRepository;
    private final CategoryRepository categoryRepository;
    private final ResponseRepository responseRepository;
    private final InterventionService interventionService;
    private final PersonService personService;

    public SurveyService(SurveyRepository surveyRepository, TemplateRepository templateRepository,
            ResponseRepository responseRepository, CategoryRepository categoryRepository,
            InterventionService interventionService, PersonService personService) {
        this.surveyRepository = surveyRepository;
        this.templateRepository = templateRepository;
        this.categoryRepository = categoryRepository;
        this.responseRepository = responseRepository;
        this.interventionService = interventionService;
        this.personService = personService;
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurvey(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException("Survey with ID " + id + " not found."));
    }

    /**
     * Get the questions for the given survey and category.
     * TODO: Take page and page size into account.
     * 
     * @param surveyId   The ID of the survey to get the questions for.
     * @param categoryId The ID of the category to get the questions for.
     * @param page       The page number.
     * @param pageSize   The number of questions per page.
     * @return The questions for the given survey and category.
     */
    public QuestionSetDto getQuestions(Long surveyId, Long categoryId, int page, int pageSize) {
        Survey survey = this.getSurvey(surveyId);
        
        if (categoryId == null) {
            return QuestionSetDto.fromEntity(surveyId, null, survey.getAllFactors());
        }
        return QuestionSetDto.fromEntity(surveyId, survey.getCategoryById(categoryId), survey.getFactorsByCategoryId(categoryId));
    }

    /**
     * Creates a new survey based on the given phase.
     *
     * @param phaseId            The ID of the phase to create the survey for.
     * @param respondentPersonId The ID of the Person object of the respondent.
     * @return The created survey.
     */
    public Survey createSurvey(Long phaseId, Long respondentPersonId) {
        try {
            Person person = personService.getPersonById(respondentPersonId);
            Phase phase = interventionService.getPhaseById(phaseId);

            Survey survey = Survey.createSurvey(phase, this.getActiveTemplate(), person);
            survey.getCategories().forEach(this::saveCategory); // todo: test this
            personService.savePerson(person);
            return surveyRepository.save(survey);
        } catch (PhaseNotFoundException | PersonNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Saves a response for the given survey.
     * 
     * @param id          The ID of the survey to save the response for.
     * @param responseDto The response data to save.
     * @return The saved response.
     * @throws SurveyNotFoundException If the survey with the given ID is not found.
     */
    public Response submitResponse(Long id, SubmitResponseDto responseDto) {
        Survey survey = this.getSurvey(id);
        Response response = survey.saveResponse(responseDto.getSubfactorId(), responseDto.getFacilitatingFactor(),
                responseDto.getPriority(), responseDto.getComment());

        responseRepository.save(response);
        surveyRepository.save(survey);
        return response;
    }

    public Template createDefaultTemplate() {
        this.createTemplateIfNotExists();
        return this.getActiveTemplate();
    }

    private Category saveCategory(Category category) {
        return categoryRepository.save(category);
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

        Subfactor subfactor1 = Subfactor.createSubfactor("First subfactor", 1, false);
        Subfactor subfactor2 = Subfactor.createSubfactor("Second subfactor", 2, true);
        Subfactor subfactor3 = Subfactor.createSubfactor("Third subfactor", 3, false);
        Subfactor subfactor4 = Subfactor.createSubfactor("Fourth subfactor", 4, true);
        Subfactor subfactor5 = Subfactor.createSubfactor("Fifth subfactor", 5, false);
        Subfactor subfactor6 = Subfactor.createSubfactor("Sixth subfactor", 6, true);
        Subfactor subfactor7 = Subfactor.createSubfactor("Seventh subfactor", 7, false);
        Subfactor subfactor8 = Subfactor.createSubfactor("Eighth subfactor", 8, true);
        
        Factor factor1 = Factor.createFactor("First factor", 1);
        Factor factor2 = Factor.createFactor("Second factor", 2);
        Factor factor3 = Factor.createFactor("Third factor", 3);
        Factor factor4 = Factor.createFactor("Fourth factor", 4);
        factor1.setSubfactors(List.of(subfactor1, subfactor2));
        factor2.setSubfactors(List.of(subfactor3, subfactor4));
        factor3.setSubfactors(List.of(subfactor5, subfactor6));
        factor4.setSubfactors(List.of(subfactor7, subfactor8));

        Category category1 = Category.createCategory("First category", "#FF0000", "This is the first category.");
        Category category2 = Category.createCategory("Second category", "#00FF00", "This is the second category.");
        category1.setFactors(List.of(factor1, factor2));
        category2.setFactors(List.of(factor3, factor4));

        Template template = Template.createTemplate(
            "Default Template",
            "Should be changed for production!",
            1,
            List.of(category1, category2)
        );

        this.templateRepository.save(template);
    }
}
