package nl.hu.greenify.core.application;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import java.io.FileReader;

import jakarta.annotation.PostConstruct;
import nl.hu.greenify.core.domain.*;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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
        Long nextCategoryId = survey.getNextCategoryId(categoryId);
        
        if (categoryId == null || categoryId == 0) {
            return QuestionSetDto.fromEntity(survey, nextCategoryId, survey.getCategories());
        }
        return QuestionSetDto.fromEntity(survey, nextCategoryId, Arrays.asList(survey.getCategoryById(categoryId)));
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

    public void deleteResponse(Long surveyId, Long subfactorId) {
        Survey survey = this.getSurvey(surveyId);
        survey.deleteResponse(subfactorId);
        surveyRepository.save(survey);
    }

    public Template createDefaultTemplate() throws Exception {
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

    private void createTemplateIfNotExists() throws Exception {
        if (this.templateRepository.count() > 0) return;
        this.createTemplateFromCsv();
    }

    private void createTemplateFromCsv() throws Exception {
        List<String[]> csvRecords = readCsv("templates/GreenIT_Template_1.csv");
        Map<String, Category> categoriesMap = new HashMap<>();
        Map<String, Factor> factorsMap = new HashMap<>();
        String lastCategoryName = null;
        String lastFactorTitle = null;

        for (int i = 2; i < csvRecords.size(); i++) {
            String[] record = csvRecords.get(i);
            if (record.length < 8) continue;

            String categoryName = record[0].isEmpty() ? lastCategoryName : record[0];
            String categoryColor = record[1];
            String categoryDescription = record[2];
            String factorTitle = record[3].isEmpty() ? lastFactorTitle : record[3];
            try {
                int factorNumber = Integer.parseInt(record[4].isEmpty() ? "-1" : record[4]);
                String subfactorTitle = record[5];
                int subfactorNumber = Integer.parseInt(record[6]);
                boolean isSupportingFactor = Boolean.parseBoolean(record[7]);

                Category category = categoriesMap.computeIfAbsent(categoryName, k -> Category.createCategory(categoryName, categoryColor, categoryDescription));
                String factorKey = categoryName + "_" + factorTitle;
                Factor factor = factorsMap.computeIfAbsent(factorKey, k -> Factor.createFactor(factorTitle, factorNumber));
                Subfactor subfactor = Subfactor.createSubfactor(subfactorTitle, subfactorNumber, isSupportingFactor);
                factor.addSubfactor(subfactor);

                if (!category.getFactors().contains(factor)) {
                    category.addFactor(factor);
                }

                lastCategoryName = categoryName;
                lastFactorTitle = factorTitle;
            } catch (NumberFormatException e) {
                continue;
            }
        }

        List<Category> categories = new ArrayList<>(categoriesMap.values());
        Template template = Template.createTemplate("Generated Template", "Generated from CSV", 1, categories);
        this.templateRepository.save(template);
    }

    private List<String[]> readCsv(String filePath) throws Exception {
        List<String[]> records = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(filePath);

        try (BufferedReader br = Files.newBufferedReader(Path.of(resource.getURI()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(values);
            }
        }
        return records;
    }
}
