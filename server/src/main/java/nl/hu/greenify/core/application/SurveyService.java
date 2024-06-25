package nl.hu.greenify.core.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.PostConstruct;
import nl.hu.greenify.core.domain.*;
import org.springframework.stereotype.Service;

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
    private final PersonService personService;

    public SurveyService(SurveyRepository surveyRepository, TemplateRepository templateRepository,
            ResponseRepository responseRepository, CategoryRepository categoryRepository,
            PersonService personService) {
        this.surveyRepository = surveyRepository;
        this.templateRepository = templateRepository;
        this.categoryRepository = categoryRepository;
        this.responseRepository = responseRepository;
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
        
        if (categoryId == null || categoryId == 0) {
            return QuestionSetDto.fromEntity(surveyId, survey.getCategories());
        }
        return QuestionSetDto.fromEntity(surveyId, Arrays.asList(survey.getCategoryById(categoryId)));
    }

    /**
     * Creates a new survey based on the given phase.
     *
     * @param phaseId            The ID of the phase to create the survey for.
     * @param respondentPersonId The ID of the Person object of the respondent.
     * @return The created survey.
     */
    public Survey createSurvey(Phase phase, Person person) {
        if (phase == null)
            throw new IllegalArgumentException("Survey should have a phase.");
            
        if (person == null)
            throw new IllegalArgumentException("Survey should have a person.");
        
        if (phase.getSurveyOfPerson(person).isPresent())
            throw new IllegalArgumentException("Person already has a survey for this phase.");
            
        Survey survey = Survey.createSurvey(phase, this.getActiveTemplate(), person);
        survey.getCategories().forEach(this::saveCategory); // todo: test this

        survey = surveyRepository.save(survey);
        personService.savePerson(person);
        return survey;
    }

    public List<Survey> createSurveysForParticipants(Phase phase, List<Person> participants) {
        return participants.stream()
                .map(person -> this.createSurvey(phase, person))
                .toList();
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

    @PostConstruct // Call method only once after bean is created
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

        // Category 1
        // For factor 1
        Subfactor subfactor1 = Subfactor.createSubfactor("De betrokkenen zien de ontwikkelaar van de interventie als legitiem", 1, true);
        Subfactor subfactor2 = Subfactor.createSubfactor("Er is transparantie in hoe de besluitvorming rondom de groene interventie is verlopen", 2, true);
        // For factor 2
        Subfactor subfactor3 = Subfactor.createSubfactor("Er is betrouwbaar ondersteunend bewijs voor de werking van de groene interventie. Naast wetenschappelijk bewijs kan dit bestaan uit verhalen van collega's en patiëntervaringen", 3, true);
        Subfactor subfactor4 = Subfactor.createSubfactor("Het bewijs is gedeeld met betrokkenen", 4, true);

        // Category 2
        // For factor 3
        Subfactor subfactor5 = Subfactor.createSubfactor("Gebruikers worden vroegtijdig betrokken bij de implementatie van de groene interventie", 5, true);
        Subfactor subfactor6 = Subfactor.createSubfactor("Er is inzicht in de behoeften en belangen van de gebruikers van de groene interventie", 6, true);
        Subfactor subfactor7 = Subfactor.createSubfactor("De behoeften en belangen van de gebruikers staan centraal binnen de implementatie en worden geïntegreerd in het implementatieplan en het ontwerp van de groene interventie", 7, true);
        // For factor 4
        Subfactor subfactor8 = Subfactor.createSubfactor("De organisatie heeft een sterk (groen) netwerk buiten de organisatie met bijvoorbeeld groene partijen, natuurexperts, vrijwilligersorganisaties, sociale initiatieven, scholen en beroepsgroepen", 8, true);
        Subfactor subfactor9 = Subfactor.createSubfactor("De organisatie gebruikt de kennis en kunde van externe partijen en blijft op de hoogte van de laatste actuele ontwikkelingen en kennis rondom groene interventies", 9, true);

        // Category 3
        // For factor 5
        Subfactor subfactor10 = Subfactor.createSubfactor("Organisatorische factoren als de structuur, de omvang, de mate van professionaliteit, hoe lang een organisatie bestaat zijn van positieve invloed op de implementatie van de groene interventie", 10, true);
        // For factor 6
        Subfactor subfactor11 = Subfactor.createSubfactor("Binnen de organisatie is er een goede samenwerking en effectieve communicatie tussen verschillende stakeholders", 11, true);

        // Factors
        Factor factor1 = Factor.createFactor("Afkomst plan", 1);
        Factor factor2 = Factor.createFactor("Kwaliteit en sterkte bewijs", 2);
        Factor factor3 = Factor.createFactor("Behoeften en wensen gebruikers", 3);
        Factor factor4 = Factor.createFactor("Netwerk externe organisaties", 4);
        Factor factor5 = Factor.createFactor("Kenmerken organisatie", 5);
        Factor factor6 = Factor.createFactor("Samenwerking en communicatie", 6);

        factor1.setSubfactors(List.of(subfactor1, subfactor2));
        factor2.setSubfactors(List.of(subfactor3, subfactor4));

        factor3.setSubfactors(List.of(subfactor5, subfactor6, subfactor7));
        factor4.setSubfactors(List.of(subfactor8, subfactor9));

        factor5.setSubfactors(List.of(subfactor10));
        factor6.setSubfactors(List.of(subfactor11));

        // Categories
        Category category1 = Category.createCategory("De groene interventie", "#FF0000", "Kenmerken van de groene interventie die van invloed zijn op de implementatie");
        Category category2 = Category.createCategory("De externe omgeving", "#00FF00", "Kenmerken van de externe omgeving die van invloed zijn op de implementatie");
        Category category3 = Category.createCategory("De organisatie", "#00FF00", "Kenmerken van de organisatie die van invloed zijn op de implementatie");
        category1.setFactors(List.of(factor1, factor2));
        category2.setFactors(List.of(factor3, factor4));
        category3.setFactors(List.of(factor5, factor6));

        Template template = Template.createTemplate(
            "Default Template",
            "Should be changed for production!",
            1,
            List.of(category1, category2, category3)
        );

        this.templateRepository.save(template);
    }
}
