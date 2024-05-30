package nl.hu.greenify.core.presentation;

import jakarta.transaction.Transactional;
import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.data.*;
import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyReportControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private InterventionRepository interventionRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ResponseRepository responseRepository;

    Long phaseId = 0L;
    @BeforeEach
    void setUp() {
        surveyRepository.deleteAll();
        templateRepository.deleteAll();
        phaseRepository.deleteAll();
        personRepository.deleteAll();
        categoryRepository.deleteAll();
        responseRepository.deleteAll();
        interventionRepository.deleteAll();

        createRepositoryData();
    }

    void createRepositoryData() {
        // Create subfactor
        System.out.println("Creating subfactor");
        Subfactor subfactor = Subfactor.createSubfactor("subfactor1", 1, true);

        // Create factor
        System.out.println("Creating factor");
        Factor factor = new Factor(1L, "factor1", 1, List.of(subfactor));
        System.out.println("Setting resopnse to null for now");
        subfactor.setResponse(null);

        // Create category
        System.out.println("Creating category");
        Category category = new Category(1L, "category1", "red", "Organizational", List.of(factor));
        System.out.println("Saving category to repository");
        this.categoryRepository.save(category);

        // Create template
        System.out.println("Creating template");
        Template template = new Template(1L, "template1", "First template version for interventions", 1, List.of(category));
        System.out.println("Saving template to repository");
        this.templateRepository.save(template);

        // Create phase
        System.out.println("Creating phase");
        Phase phase = new Phase(PhaseName.INITIATION);
        System.out.println("Saving phase to repository");
        this.phaseRepository.save(phase);
        System.out.println("Set ID of phase");
        this.phaseId = phase.getId();


        // Create person1
        System.out.println("Creating person");
        Person person = new Person("John", "Doe", "johndoe@example.com");
        System.out.println("Saving person to repository");
        this.personRepository.save(person);

        // Create person2
        System.out.println("Creating person");
        Person person2 = new Person("Jane", "Doe", "janedoe@example.com");
        System.out.println("Saving person to repository");
        this.personRepository.save(person2);

        // Survey creations based on template:
        System.out.println("Creating survey");
        Survey survey = Survey.createSurvey(phase, Template.copyOf(template), person);
        System.out.println("Creating survey");
        Survey survey2 = Survey.createSurvey(phase, Template.copyOf(template), person2);

        // Add survey to phase
        System.out.println("Adding survey to phase");
        phase.addSurvey(survey);
        phase.addSurvey(survey2);
        System.out.println("Saving phase to repository");
        this.phaseRepository.save(phase);

        System.out.println("=====================================");
        System.out.println(phase.getId());

        setupProvideResponseSurvey1(survey);
        setupProvideResponseSurvey1(survey2);
    }

    void setupProvideResponseSurvey1(Survey survey) {
        Subfactor subfactor = survey.getCategories().get(0).getFactors().get(0).getSubfactors().get(0);
        // Create response
        Response response = Response.createResponse(subfactor, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        subfactor.setResponse(response);
        categoryRepository.save(survey.getCategories().get(0));
        responseRepository.save(response);
    }


    @AfterEach
    void tearDown() {
        responseRepository.deleteAll();
        surveyRepository.deleteAll();
        templateRepository.deleteAll();
        categoryRepository.deleteAll();
        phaseRepository.deleteAll();
        interventionRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Obtain category scores of non existing phase provides a message to tell the user it does not exist")
    void getCategoryScoresOfNonExistingPhaseTest() throws Exception {
        Long phaseId = 0L;
        RequestBuilder request = MockMvcRequestBuilders.get("/survey-report/{phaseId}/category-scores", phaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Phase with id " + phaseId + " does not exist")));
    }

    @Test
    @DisplayName("Obtain category scores of a phase provides the average and maximum possible scores of the categories")
    void getCategoryScoresOfPhaseTest() throws Exception {
        Long phaseId = this.phaseId;
        RequestBuilder request = MockMvcRequestBuilders
                .get("/survey-report/{phaseId}/category-scores", phaseId);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxScore", is(10.0)));
    }

    @Test
    @DisplayName("Test getting category scores for a phase")
    public void testGetCategoryScores() throws Exception {
        Long phaseId = this.phaseId;
        RequestBuilder request = MockMvcRequestBuilders.get("/survey-report/{phaseId}/category-scores", phaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoryName", is("category1")))
                .andExpect(jsonPath("$[0].maxScore", is(10.0)))
                .andExpect(jsonPath("$[0].averageScore", is(2.0)));
    }

    @Test
    @DisplayName("Test getting subfactor scores for a category in a phase")
    public void testGetSubfactorScores() throws Exception {
        Long phaseId = this.phaseId;
        String categoryName = "category1";

        RequestBuilder request = MockMvcRequestBuilders.get("/survey-report/{phaseId}/subfactor-scores/{categoryName}", phaseId, categoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subfactorName", is("subfactor1")))
                .andExpect(jsonPath("$[0].maxScore", is(10.0)))
                .andExpect(jsonPath("$[0].averageScore", is(2.0)));
    }

    @Test
    @DisplayName("Troubleshooting")
    void troubleShooting() throws Exception {
        Long phaseId = this.phaseId;
        RequestBuilder request = MockMvcRequestBuilders
                .get("/survey");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxScore", is(10.0)));
    }
}
