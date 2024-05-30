package nl.hu.greenify.core.presentation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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

import nl.hu.greenify.core.application.SurveyService;
import nl.hu.greenify.core.application.exceptions.PersonNotFoundException;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.presentation.dto.CreateSurveyDto;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerIntegrationTest {
    private static final Long PHASE_ID = 1L;    
    private static final Long PERSON_ID = 1L;    
    private static final Long SURVEY_ID = 1L;

    private Person person;
    private Phase phase;
    private Survey survey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @BeforeEach
    void setup() {
        this.person = new Person(PERSON_ID, "John", "Doe", "johndoe@example.com", new ArrayList<>());
        this.phase = new Phase(PHASE_ID, PhaseName.INITIATION);
        this.survey = new Survey(SURVEY_ID, this.phase, new ArrayList<>(), this.person);
    }

    /**
     * createSurvey tests
    */

    @Test
    @DisplayName("Creating a survey should return a new survey")
    void createSurveyShouldReturnOk() throws Exception {
        when(surveyService.createSurvey(PHASE_ID, PERSON_ID)).thenReturn(this.survey);

        CreateSurveyDto dto = new CreateSurveyDto(PHASE_ID, PERSON_ID);
        RequestBuilder request = MockMvcRequestBuilders.post("/survey")
                .contentType("application/json")
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Creating a survey with invalid phase should return 400")
    void createSurveyWithInvalidPhaseShouldReturnNotFound() throws Exception {
        when(surveyService.createSurvey(PHASE_ID, PERSON_ID)).thenThrow(new IllegalArgumentException(""));

        CreateSurveyDto dto = new CreateSurveyDto(PHASE_ID, PERSON_ID);
        RequestBuilder request = MockMvcRequestBuilders.post("/survey")
                .contentType("application/json")
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Creating a survey with invalid person should return 400")
    void createSurveyWithInvalidPersonShouldReturnNotFound() throws Exception {
        when(surveyService.createSurvey(PHASE_ID, PERSON_ID)).thenThrow(new IllegalArgumentException(""));

        CreateSurveyDto dto = new CreateSurveyDto(PHASE_ID, PERSON_ID);
        RequestBuilder request = MockMvcRequestBuilders.post("/survey")
                .contentType("application/json")
                .content(dto.toJsonString());
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    /**
     * getSurveyQuestions tests
     */

    @Test
    @DisplayName("Getting survey questions should return 200")
    void getSurveyQuestionsShouldReturnOk() throws Exception {
        when(surveyService.getQuestions(SURVEY_ID, 1L, 1, 1000)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
                .param("categoryId", "1")
                .param("page", "1")
                .param("pageSize", "1000");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Getting survey questions with invalid survey id should return 404")
    void getSurveyQuestionsWithInvalidSurveyIdShouldReturnNotFound() throws Exception {
        when(surveyService.getQuestions(SURVEY_ID, 1L, 1, 1000)).thenThrow(new PersonNotFoundException(""));

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
                .param("categoryId", "1")
                .param("page", "1")
                .param("pageSize", "1000");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Getting survey questions with invalid category id should return 200")
    void getSurveyQuestionsWithInvalidCategoryIdShouldReturnOk() throws Exception {
        when(surveyService.getQuestions(SURVEY_ID, 0L, 1, 1000)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
                .param("categoryId", "0")
                .param("page", "1")
                .param("pageSize", "1000");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Getting survey questions without a category id should return 200")
    void getSurveyQuestionsWithoutCategoryIdShouldReturnOk() throws Exception {
        when(surveyService.getQuestions(SURVEY_ID, 1L, 1, 1000)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
                .param("page", "1")
                .param("pageSize", "1000");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
    
    // NOTE: The following tests are not necessary because the page and pageSize are not taken into account yet.
    
    // @Test
    // @DisplayName("Getting survey questions without a page and its size should return 200")
    // void getSurveyQuestionsWithoutPageAndSizeShouldReturnOk() throws Exception {
    //     when(surveyService.getQuestions(SURVEY_ID, 1L, 0, 0)).thenReturn(null);

    //     RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
    //             .param("categoryId", "1");

    //     mockMvc.perform(request)
    //             .andExpect(status().isOk());
    // }

    // @Test
    // @DisplayName("Getting survey questions with invalid page and its size should return 200")
    // void getSurveyQuestionsWithInvalidPageAndSizeShouldReturnOk() throws Exception {
    //     when(surveyService.getQuestions(SURVEY_ID, 1L, 0, 0)).thenReturn(null);

    //     RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID + "/questions")
    //             .param("categoryId", "1")
    //             .param("page", "0")
    //             .param("pageSize", "0");

    //     mockMvc.perform(request)
    //             .andExpect(status().isOk());
    // }
}
