package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.data.*;
import nl.hu.greenify.core.domain.*;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyReportControllerIntegrationTest2 {

    @Autowired
    MockMvc mockMvc;

    private Phase phase;
    private Subfactor subfactor1Survey1;
    private Subfactor subfactor2Survey1;
    private Subfactor subfactor3Survey1;
    private Subfactor subfactor4Survey1;
    private Subfactor subfactor1Survey2;
    private Subfactor subfactor2Survey2;
    private Subfactor subfactor3Survey2;
    private Subfactor subfactor4Survey2;
    private Long phaseId = 0L;

    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private ResponseRepository responseRepository;

    @BeforeEach
    void setUp() {
        // Clear all repositories
        phaseRepository.deleteAll();

        // Template creation steps:
        // 1. Create categories
        Category categoryTemplate1 = new Category(1L, "name", "red", "description", new ArrayList<>());
        Category categoryTemplate2 = new Category(2L, "name2", "blue", "description2", new ArrayList<>());

        // 2. Create factors
        Factor factorTemplate1 = new Factor(1L, "title", 1, new ArrayList<>());
        Factor factorTemplate2 = new Factor(2L, "title2", 2, new ArrayList<>());

        // 3. Attach each factor to a category
        categoryTemplate1.addFactor(factorTemplate1);
        categoryTemplate2.addFactor(factorTemplate2);

        // 4. Create subfactors
        Subfactor subfactorTemplate1 = new Subfactor(1L, "title", 1, true);
        Subfactor subfactorTemplate2 = new Subfactor(2L, "title2", 2, true);
        Subfactor subfactorTemplate3 = new Subfactor(3L, "title3", 3, false);
        Subfactor subfactorTemplate4 = new Subfactor(4L, "title4", 4, false);

        // 5. Attach each subfactor to a factor
        factorTemplate1.addSubfactor(subfactorTemplate1);
        factorTemplate1.addSubfactor(subfactorTemplate2);
        factorTemplate2.addSubfactor(subfactorTemplate3);
        factorTemplate2.addSubfactor(subfactorTemplate4);

        // TEMP FIX: Set response to null for now
        subfactorTemplate1.setResponse(null);
        subfactorTemplate2.setResponse(null);
        subfactorTemplate3.setResponse(null);
        subfactorTemplate4.setResponse(null);

        // TEMP FIX: create responses for the subfactors
        Response response1 = Response.createResponse(subfactorTemplate1, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response response2 = Response.createResponse(subfactorTemplate2, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);
        Response response3 = Response.createResponse(subfactorTemplate3, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response response4 = Response.createResponse(subfactorTemplate4, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);

        // TEMP FIX: save the responses to the repository
        this.responseRepository.save(response1);
        this.responseRepository.save(response2);
        this.responseRepository.save(response3);
        this.responseRepository.save(response4);

        // Save the category to the repository
        this.categoryRepository.save(categoryTemplate1);
        this.categoryRepository.save(categoryTemplate2);

        // 6. Create a template with the categories
        Template template = new Template(1L, "name", "description", 1, List.of(categoryTemplate1, categoryTemplate2));

        // Save the template to the repository
        this.templateRepository.save(template);

        // Phase creation to create surveys for based on the template:
        this.phase = new Phase(PhaseName.INITIATION);

        // Person creation to set as the respondant:
        Person person1 = new Person("John", "Doe", "johndoe@example.com");
        Person person2 = new Person("Jane", "Doe", "janedoe@example.com");

        // Save the person to the repository
        this.personRepository.save(person1);
        this.personRepository.save(person2);

        // Survey creations based on templates:
        Survey.createSurvey(phase, Template.copyOf(template), person1);
        Survey.createSurvey(phase, Template.copyOf(template), person2);

        // Prepare subfactors that can be used to provide responses on
        setupProvideResponseSurvey1();
        setupProvideResponseSurvey2();

        // Actually provide responses on the subfactors
        setupResponses();

        // Save all the created data to the repositories
        this.phaseRepository.save(phase);

        // Get the ID of the phase
        this.phaseId = phase.getId();
    }

    // Helper methods to prepare for responses for the surveys
    void setupProvideResponseSurvey1() {
        Survey survey = phase.getSurveys().get(0);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor1Survey1 = factor.getSubfactors().get(0);
        this.subfactor2Survey1 = factor.getSubfactors().get(1);

        Category category2 = survey.getCategories().get(1);
        Factor factor2 = category2.getFactors().get(0);
        this.subfactor3Survey1 = factor2.getSubfactors().get(0);
        this.subfactor4Survey1 = factor2.getSubfactors().get(1);
    }

    void setupProvideResponseSurvey2() {
        Survey survey = phase.getSurveys().get(1);

        Category category = survey.getCategories().get(0);
        Factor factor = category.getFactors().get(0);
        this.subfactor1Survey2 = factor.getSubfactors().get(0);
        this.subfactor2Survey2 = factor.getSubfactors().get(1);

        Category category2 = survey.getCategories().get(1);
        Factor factor2 = category2.getFactors().get(0);
        this.subfactor3Survey2 = factor2.getSubfactors().get(0);
        this.subfactor4Survey2 = factor2.getSubfactors().get(1);
    }

    void setupResponses() {
        Response.createResponse(subfactor1Survey1, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response.createResponse(subfactor2Survey1, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);
        Response.createResponse(subfactor3Survey1, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response.createResponse(subfactor4Survey1, FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, null);
        Response.createResponse(subfactor1Survey2, FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, null);
        Response.createResponse(subfactor2Survey2, FacilitatingFactor.AGREE, Priority.LITTLE_PRIORITY, null);
        Response.createResponse(subfactor3Survey2, FacilitatingFactor.TOTALLY_AGREE, Priority.LITTLE_PRIORITY, null);
        Response.createResponse(subfactor4Survey2, FacilitatingFactor.DISAGREE, Priority.TOP_PRIORITY, null);
    }

    @Test
    @DisplayName("Test phase controller first for trouble shooting reasons, just obtaining a phase as a whole")
    void testPhaseController() throws Exception {
        Long phaseId = this.phaseId;

        RequestBuilder request = MockMvcRequestBuilders
                .get("/survey");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
}
