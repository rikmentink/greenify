package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.application.PersonService;
import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.data.PhaseRepository;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.enums.PhaseName;
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
    Person person;
    Intervention i;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "johndoe@gmail.com");
        person.setId(1L);
        i = new Intervention("Intervention", "Intervention description", person);
        i.setId(1L);

        when(interventionRepository.findById(1L)).thenReturn(Optional.of(i));
        when(phaseRepository.findById(1L)).thenReturn(Optional.of(new Phase(PhaseName.PLANNING)));
        when(personService.getPersonById(1L)).thenReturn(person);
    }

    @Test
    @DisplayName("Add an intervention")
    void addInterventionTest() throws Exception {
        String name = "Garden";
        String description = "Watering the plants";

        RequestBuilder request = MockMvcRequestBuilders.post("/intervention/create")
                .param("name", name)
                .param("description", description)
                .param("adminId", "1");

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Add a phase to an intervention")
    void addPhaseToInterventionTest() throws Exception {
        String name = "Init";
        PhaseName phaseName = PhaseName.INITIATION;
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.post("/intervention/phase/{id}", id)
                .param("name", name)
                .param("personId", id.toString())
                .param("phaseName", String.valueOf(phaseName));

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Fetching a phase")
    void getPhaseTest() throws Exception {
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/phase/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Fetching a non-existing phase")
    void getNonExistingPhaseTest() throws Exception {
        Long id = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/phase/{id}", id)
                .param("id", id.toString());

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
    @DisplayName("Fetching an invalid intervention")
    void getInvalidInterventionTest() throws Exception {
        Long id = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }








//    @Test
//    @DisplayName("Fetching a nonexisting person")
//    void getNonExistingPersonTest() throws Exception {
//        Long id = 2L;
//
//        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
//                .param("id", id.toString());
//
//        mockMvc.perform(request)
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Fetching a person")
//    void getPersonTest() throws Exception {
//        Long id = 1L;
//
//        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
//                .param("id", id.toString());
//
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.interventions").isArray())
//                .andExpect(jsonPath("$.interventions", hasSize(1)));
//    } for personControllerIntegrationTest
}
