package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.application.SurveyService;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.presentation.dto.CreateInterventionDto;
import nl.hu.greenify.core.presentation.dto.CreatePhaseDto;
import nl.hu.greenify.security.application.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InterventionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PhaseRepository phaseRepository;
    @MockBean
    private InterventionRepository interventionRepository;
    @MockBean
    private PersonService personService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private SurveyService surveyService;

    Person person;
    Intervention intervention;
    Phase phase;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "John", "Doe", "johndoe@gmail.com", new ArrayList<>());
        phase = new Phase(1L, PhaseName.PLANNING);
        intervention = new Intervention(1L, "Intervention", "Intervention description", person, new ArrayList<>(), new ArrayList<>());
        intervention.addPhase(phase);
        intervention.addParticipant(person);

        when(interventionRepository.findById(1L)).thenReturn(Optional.of(intervention));
        when(phaseRepository.findById(1L)).thenReturn(Optional.of(phase));
        when(personService.getPersonById(1L)).thenReturn(person);
        when(accountService.getCurrentPerson()).thenReturn(person);
    }

    @Test
    @DisplayName("Add an intervention")
    void addInterventionTest() throws Exception {
        String name = "Garden";
        String description = "Watering the plants";

        CreateInterventionDto dto = new CreateInterventionDto(1L, name, description);
        RequestBuilder request = MockMvcRequestBuilders.post("/intervention")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Add a pse to an intervention")
    void addPhaseToInterventionTest() throws Exception {
        PhaseName phaseName = PhaseName.INITIATION;
        String description = "Phase description with more info";
        Long id = 1L;

        CreatePhaseDto dto = new CreatePhaseDto(phaseName, description);
        RequestBuilder request = MockMvcRequestBuilders.post("/intervention/{id}/phase", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dto.toJsonString());

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Fetching a phase")
    void getPhaseTest() throws Exception {
        Long interventionId = intervention.getId();
        Long phaseId = phase.getId();

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{interventionId}/phase/{phaseId}", interventionId, phaseId)
                .param("interventionId", interventionId.toString())
                .param("phaseId", phaseId.toString());

        mockMvc.perform(request);

}

    @Test
    @DisplayName("Fetching a non-existing phase")
    void getNonExistingPhaseTest() throws Exception {
        Long interventionId = 1L;
        Long phaseId = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{interventionId}/phase/{phaseId}", interventionId, phaseId)
                .param("interventionId", interventionId.toString())
                .param("phaseId", phaseId.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fetching all interventions by a person")
    void getAllInterventionsByPersonTest() throws Exception {
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/all/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Fetching all interventions by a nonexisting person")
    void getAllInterventionsByNonExistingPersonTest() throws Exception {
        Long id = 10L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/all/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fetching an intervention")
    void getInterventionTest() throws Exception {
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Fetching a non-existent intervention")
    void getInvalidInterventionTest() throws Exception {
        Long id = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}