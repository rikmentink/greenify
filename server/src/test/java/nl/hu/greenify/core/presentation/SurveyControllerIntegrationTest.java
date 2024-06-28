package nl.hu.greenify.core.presentation;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import nl.hu.greenify.core.application.exceptions.SurveyNotFoundException;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.presentation.dto.SubmitResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerIntegrationTest {
    private static final Long PHASE_ID = 1L;    
    private static final Long PERSON_ID = 1L;    
    private static final Long SURVEY_ID = 1L;
    private static final Long SUBFACTOR_ID = 1L;

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
        this.phase = new Phase(PHASE_ID, PhaseName.INITIATION, "Description");
        this.survey = new Survey(SURVEY_ID, this.phase, new ArrayList<>(), this.person);
    }

    /**
     * getSurvey tests
     */

    @Test
    @DisplayName("Getting all surveys should return 200")
    void getAllSurveysShouldReturnOk() throws Exception {
        when(surveyService.getAllSurveys()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/survey");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Getting all surveys while no surveys are available should return 200")
    void getAllSurveysWhileNoSurveysAreAvailableShouldReturnOk() throws Exception {
        when(surveyService.getAllSurveys()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/survey");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    /**
     * getSurvey tests
     */

    @Test
    @DisplayName("Getting a survey should return 200")
    void getSurveyShouldReturnOk() throws Exception {
        when(surveyService.getSurvey(SURVEY_ID)).thenReturn(this.survey);

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Getting a survey with invalid id should return 404")
    void getSurveyWithInvalidIdShouldReturnNotFound() throws Exception {
        when(surveyService.getSurvey(SURVEY_ID)).thenThrow(new PersonNotFoundException(""));

        RequestBuilder request = MockMvcRequestBuilders.get("/survey/" + SURVEY_ID);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
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

    /**
     * submitResponse tests
     */
    
    @Test
    @DisplayName("Submitting a response should return 200")
    void submitResponseShouldReturnOk() throws Exception {
        SubmitResponseDto dto = new SubmitResponseDto(1L, FacilitatingFactor.PENDING, Priority.PENDING, "");
        when(surveyService.submitResponse(SURVEY_ID, dto)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.post("/survey/" + SURVEY_ID + "/response")
                .contentType("application/json")
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Submitting a response with invalid survey id should return 404")
    void submitResponseWithInvalidSurveyIdShouldReturnNotFound() throws Exception {
        SubmitResponseDto dto = new SubmitResponseDto(1L, FacilitatingFactor.PENDING, Priority.PENDING, "");
        when(surveyService.submitResponse(SURVEY_ID, dto)).thenThrow(new SurveyNotFoundException(""));

        RequestBuilder request = MockMvcRequestBuilders.post("/survey/" + SURVEY_ID + "/response")
                .contentType("application/json")
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Submitting a response with invalid subfactor id should return 400")
    void submitResponseWithInvalidSubfactorIdShouldReturnBadRequest() throws Exception {
        SubmitResponseDto dto = new SubmitResponseDto(0L, FacilitatingFactor.PENDING, Priority.PENDING, "");
        when(surveyService.submitResponse(SURVEY_ID, dto)).thenThrow(new IllegalArgumentException(""));

        RequestBuilder request = MockMvcRequestBuilders.post("/survey/" + SURVEY_ID + "/response")
                .contentType("application/json")
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deleting a response should return 200")
    void deleteResponseShouldReturnOk() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/survey/" + SURVEY_ID + "/response/" + SUBFACTOR_ID);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deleting a response with invalid survey ID should return 404")
    void deleteResponseShouldReturnBadRequest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/survey/" + SURVEY_ID + "/response/" + SUBFACTOR_ID);
        // Throw exception when deleteResponse is called
        doThrow(new SurveyNotFoundException("")).when(surveyService).deleteResponse(SURVEY_ID, SUBFACTOR_ID);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}
