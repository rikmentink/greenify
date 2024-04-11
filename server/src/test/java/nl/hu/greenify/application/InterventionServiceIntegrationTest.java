package nl.hu.greenify.application;

import nl.hu.greenify.data.GreenifyRepository;
import nl.hu.greenify.domain.Person;
import nl.hu.greenify.domain.enums.PhaseName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InterventionServiceIntegrationTest {
    @Autowired
    private InterventionService interventionService;
    @MockBean
    private GreenifyRepository greenifyRepository;
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "johndoe@gmail.com");
        interventionService.addIntervention("Garden", "Watering the plants", person);
        interventionService.addPhaseToIntervention("Garden", PhaseName.INITIATION, person);
        when(greenifyRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
    }

    @Test
    @DisplayName("Intervention should be added to the user")
    void addIntervention() {
        assertEquals(1, person.getInterventions().size());
    }

    @Test
    @DisplayName("Phase should be added to the intervention")
    void addPhaseToIntervention() {
        assertEquals(1, person.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("Multiple phases should be added to multiple interventions")
    void addMultiplePhasesToInterventions() {
        interventionService.addIntervention("Garden2", "Watering the plants", person);
        interventionService.addPhaseToIntervention("Garden2", PhaseName.INITIATION, person);
        interventionService.addPhaseToIntervention("Garden2", PhaseName.EXECUTION, person);
        assertEquals(1, person.getInterventions().get(0).getPhases().size());
        assertEquals(2, person.getInterventions().get(1).getPhases().size());
    }

    @Test
    @DisplayName("Phase with the same name should not be added to the same intervention")
    void addDuplicatePhaseToIntervention() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", PhaseName.INITIATION, person));
    }

    @Test
    @DisplayName("The user should be fetched when asked from the database")
    void fetchUser() {
        assertEquals(person, interventionService.getPersonById(1L));
    }

    @Test
    @DisplayName("The user should not be fetched when the id is invalid")
    void fetchInvalidUser() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.getPersonById(5L));
    }

    @Test
    @DisplayName("The user should not be fetched when the id is null")
    void fetchNullUser() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.getPersonById(null));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the intervention it belongs to is incorrect")
    void addPhaseToInterventionWithIncorrectInterventionName() {
        assertThrows(NoSuchElementException.class, () -> interventionService.addPhaseToIntervention("Garden5", PhaseName.INITIATION, person));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the phase is incorrect")
    void addPhaseToInterventionWithIncorrectPhaseName() {
        assertThrows(IllegalArgumentException.class, () -> interventionService.addPhaseToIntervention("Garden", null, person));
    }
}
