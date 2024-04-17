package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.data.GreenifyRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;

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
        interventionService.addPhase("Garden", PhaseName.INITIATION, person);
        Mockito.when(greenifyRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
    }

    @Test
    @DisplayName("Intervention should be added to the user")
    void addIntervention() {
        Assertions.assertEquals(1, person.getInterventions().size());
    }

    @Test
    @DisplayName("Phase should be added to the intervention")
    void addPhaseToIntervention() {
        Assertions.assertEquals(1, person.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("Multiple phases should be added to multiple interventions")
    void addMultiplePhasesToInterventions() {
        interventionService.addIntervention("Garden2", "Watering the plants", person);
        interventionService.addPhase("Garden2", PhaseName.INITIATION, person);
        interventionService.addPhase("Garden2", PhaseName.EXECUTION, person);
        Assertions.assertEquals(1, person.getInterventions().get(0).getPhases().size());
        Assertions.assertEquals(2, person.getInterventions().get(1).getPhases().size());
    }

    @Test
    @DisplayName("Phase with the same name should be added to the same intervention")
    void addDuplicatePhaseToIntervention() {
        interventionService.addPhase("Garden", PhaseName.INITIATION, person);
        interventionService.addPhase("Garden", PhaseName.INITIATION, person);
        Assertions.assertEquals(3, person.getInterventions().get(0).getPhases().size());
    }

    @Test
    @DisplayName("The user should be fetched when asked from the database")
    void fetchUser() {
        Assertions.assertEquals(person, interventionService.getPersonById(1L));
    }

    @Test
    @DisplayName("The user should not be fetched when the id is invalid")
    void fetchInvalidUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interventionService.getPersonById(5L));
    }

    @Test
    @DisplayName("The user should not be fetched when the id is null")
    void fetchNullUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interventionService.getPersonById(null));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the intervention it belongs to is incorrect")
    void addPhaseToInterventionWithIncorrectInterventionName() {
        Assertions.assertThrows(NoSuchElementException.class, () -> interventionService.addPhase("Garden5", PhaseName.INITIATION, person));
    }

    @Test
    @DisplayName("A phase should not be added when the name of the phase is incorrect")
    void addPhaseToInterventionWithIncorrectPhaseName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interventionService.addPhase("Garden", null, person));
    }
}
