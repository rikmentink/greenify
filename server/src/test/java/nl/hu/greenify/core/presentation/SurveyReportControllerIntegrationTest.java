package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.data.*;
import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import nl.hu.greenify.security.application.AccountService;
import nl.hu.greenify.security.domain.AccountCredentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
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

    @MockBean
    private AccountService accountService;

    Long phaseId = 0L;
    Long surveyId1 = 0L;
    Long surveyId2 = 0L;
    @BeforeEach
    void setUp() {
        surveyRepository.deleteAll();
        templateRepository.deleteAll();
        categoryRepository.deleteAll();
        phaseRepository.deleteAll();
        responseRepository.deleteAll();
        interventionRepository.deleteAll();
        personRepository.deleteAll();

        createRepositoryData();

        when(accountService.login("johndoe@gmail.com", "password"))
                .thenReturn(new AccountCredentials("johndoe@gmail.com",
                        List.of(new SimpleGrantedAuthority("ROLE_MANAGER"),
                                new SimpleGrantedAuthority("ROLE_USER"),
                                new SimpleGrantedAuthority("ROLE_VUMEDEWERKER"))));
    }

    void createRepositoryData() {
        // Create person1
        Person person = Person.createPerson("John", "Doe", "johndoe@example.com");
        this.personRepository.save(person);

        // Create person2
        Person person2 = Person.createPerson("Jane", "Doe", "janedoe@example.com");
        this.personRepository.save(person2);

        // Create intervention
        Intervention intervention = Intervention.createIntervention("intervention1", "First intervention", person);
        this.interventionRepository.save(intervention);

        // Add member to intervention
        intervention.addParticipant(person2);
        this.interventionRepository.save(intervention);

        // Create subfactor
        Subfactor subfactor = Subfactor.createSubfactor("subfactor1", 1, true);
        Subfactor subfactor2 = Subfactor.createSubfactor("subfactor2", 2, true);
        Subfactor subfactor3 = Subfactor.createSubfactor("subfactor3", 3, true);

        // Create factor
        Factor factor = new Factor(1L, "factor1", 1, List.of(subfactor));
        subfactor.setResponse(null);

        Factor factor2 = new Factor(2L, "factor2", 2, List.of(subfactor2));
        subfactor.setResponse(null);

        Factor factor3 = new Factor(3L, "factor3", 3, List.of(subfactor3));
        subfactor.setResponse(null);

        // Create category
        Category category = new Category(1L, "category1", "red", "Organizational", List.of(factor));
        this.categoryRepository.save(category);

        Category category2 = new Category(2L, "category2", "blue", "Organizational", List.of(factor2));
        this.categoryRepository.save(category2);

        Category category3 = new Category(3L, "category3", "green", "Organizational", List.of(factor3));
        this.categoryRepository.save(category3);

        // Create template
        System.out.println("Creating template");
        Template template = new Template(1L, "template1", "First template version for interventions", 1, List.of(category, category2, category3));
        System.out.println("Saving template to repository");
        this.templateRepository.save(template);

        // Create phase
        System.out.println("Creating phase");
        Phase phase = Phase.createPhase(intervention, PhaseName.DEVELOPMENT, "Description");
        System.out.println("Saving phase to repository");
        this.phaseRepository.save(phase);
        System.out.println("Set ID of phase");
        this.phaseId = phase.getId();

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

        // Save survey to repository
        this.surveyRepository.save(survey);
        this.surveyRepository.save(survey2);

        this.surveyId1 = survey.getId();
        this.surveyId2 = survey2.getId();

        // Create a response on the subfactor of one of the created surveys
        Subfactor surveySubfactor = survey.getCategories().get(0).getFactors().get(0).getSubfactors().get(0);
        Subfactor surveySubfactor2 = survey.getCategories().get(1).getFactors().get(0).getSubfactors().get(0);
        Subfactor surveySubfactor3 = survey.getCategories().get(2).getFactors().get(0).getSubfactors().get(0);

        // Create some more responses on subfactors, but through a different survey
        Subfactor survey2Subfactor = survey2.getCategories().get(0).getFactors().get(0).getSubfactors().get(0);

        // Save the response to the repository
        this.responseRepository.save(Response.createResponse(surveySubfactor, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null));
        this.surveyRepository.save(survey);

        this.responseRepository.save(Response.createResponse(surveySubfactor2, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null));
        this.surveyRepository.save(survey);

        this.responseRepository.save(Response.createResponse(surveySubfactor3, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null));
        this.surveyRepository.save(survey);

        this.responseRepository.save(Response.createResponse(survey2Subfactor, FacilitatingFactor.TOTALLY_AGREE, Priority.NO_PRIORITY, null));
        this.surveyRepository.save(survey2);
    }


    @AfterEach
    void tearDown() {
        surveyRepository.deleteAll();
        templateRepository.deleteAll();
        categoryRepository.deleteAll();
        phaseRepository.deleteAll();
        responseRepository.deleteAll();
        interventionRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Obtain category scores of non existing phase provides a message to tell the user it does not exist")
    void getCategoryScoresOfNonExistingPhaseTest() throws Exception {
        accountService.register("johndoe@gmail.com", "password", "John", "Doe");

        String loginBody = "{\"email\": \"johndoe@gmail.com\", \"password\": \"password\"}";
        RequestBuilder requestLogin = MockMvcRequestBuilders.post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody);

        MvcResult loginResult = mockMvc.perform(requestLogin)
                .andExpect(status().isOk())
                .andReturn();

        String authHeader = loginResult.getResponse().getHeader("Authorization");

        Long phaseId = 0L;
        RequestBuilder request = MockMvcRequestBuilders.get("/survey-report/{phaseId}/category-scores", phaseId)
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Phase with id " + phaseId + " does not exist")));
    }

    @Test
    @DisplayName("Obtain category scores of a phase provides the average and maximum possible scores of the categories")
    void getCategoryScoresOfPhaseTest() throws Exception {
        accountService.register("johndoe@gmail.com", "password", "John", "Doe");

        String loginBody = "{\"email\": \"johndoe@gmail.com\", \"password\": \"password\"}";
        RequestBuilder requestLogin = MockMvcRequestBuilders.post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody);

        MvcResult loginResult = mockMvc.perform(requestLogin)
                .andExpect(status().isOk())
                .andReturn();

        String authHeader = loginResult.getResponse().getHeader("Authorization");

        Long phaseId = this.phaseId;
        RequestBuilder request = MockMvcRequestBuilders
                .get("/survey-report/{phaseId}/category-scores", phaseId)
                .header("Authorization", authHeader);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].categoryName", containsInAnyOrder("category1", "category2", "category3")))

                .andExpect(jsonPath("$[?(@.categoryName=='category1')].maxPossibleScore", contains(20.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category1')].totalScore", contains(15.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category1')].percentage", contains(75)))

                .andExpect(jsonPath("$[?(@.categoryName=='category2')].maxPossibleScore", contains(10.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category2')].totalScore", contains(10.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category2')].percentage", contains(100)))

                .andExpect(jsonPath("$[?(@.categoryName=='category3')].maxPossibleScore", contains(10.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category3')].totalScore", contains(10.0)))
                .andExpect(jsonPath("$[?(@.categoryName=='category3')].percentage", contains(100)));
    }

    @Test
    @DisplayName("Test getting subfactor scores for a category in a phase")
    public void testGetSubfactorScores() throws Exception {
        accountService.register("johndoe@gmail.com", "password", "John", "Doe");

        String loginBody = "{\"email\": \"johndoe@gmail.com\", \"password\": \"password\"}";
        RequestBuilder requestLogin = MockMvcRequestBuilders.post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody);

        MvcResult loginResult = mockMvc.perform(requestLogin)
                .andExpect(status().isOk())
                .andReturn();

        String authHeader = loginResult.getResponse().getHeader("Authorization");

        Long phaseId = this.phaseId;
        String categoryName = "category1";

        RequestBuilder request = MockMvcRequestBuilders.get("/survey-report/{phaseId}/subfactor-scores/{categoryName}", phaseId, categoryName)
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subfactorName", is("subfactor1")))
                .andExpect(jsonPath("$[0].maxPossibleScore", is(10.0)))
                .andExpect(jsonPath("$[0].averageScore", is(7.5)))
                .andExpect(jsonPath("$[0].percentage", is(75)));
    }
}
