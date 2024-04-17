package nl.hu.greenify.core.presentation;

import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.domain.Person;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InterventionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonRepository personRepository;
    Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "johndoe@gmail.com");
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        person.addIntervention("Init", "Initiation");
    }

    @Test
    @DisplayName("Add an intervention")
    void addInterventionTest() throws Exception {
        String name = "Garden";
        String description = "Watering the plants";
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.post("/intervention/{id}", id)
                .param("name", name)
                .param("id", id.toString())
                .param("description", description);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interventions").isArray())
                .andExpect(jsonPath("$.interventions", hasSize(2)));
    }

    @Test
    @DisplayName("Add a phase to an intervention")
    void addPhaseToInterventionTest() throws Exception {
        String name = "Init";
        PhaseName phaseName = PhaseName.INITIATION;
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.post("/intervention/phase/{personId}", id)
                .param("name", name)
                .param("personId", id.toString())
                .param("phaseName", String.valueOf(phaseName));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interventions").isArray())
                .andExpect(jsonPath("$.interventions", hasSize(1)))
                .andExpect(jsonPath("$.interventions[0].phases").isArray())
                .andExpect(jsonPath("$.interventions[0].phases", hasSize(1)));
    }

    @Test
    @DisplayName("Fetching a nonexisting intervention")
    void getNonExistingInterventionTest() throws Exception {
        Long id = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fetching a nonexisting person")
    void getNonExistingPersonTest() throws Exception {
        Long id = 2L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Fetching a person")
    void getPersonTest() throws Exception {
        Long id = 1L;

        RequestBuilder request = MockMvcRequestBuilders.get("/intervention/{id}", id)
                .param("id", id.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interventions").isArray())
                .andExpect(jsonPath("$.interventions", hasSize(1)));
    }
}
