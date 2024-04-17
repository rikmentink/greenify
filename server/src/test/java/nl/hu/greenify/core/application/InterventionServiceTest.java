package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.data.PhaseRepository;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class InterventionServiceTest {
    private final InterventionRepository interventionRepository = mock(InterventionRepository.class);
    private final PhaseRepository phaseRepository = mock(PhaseRepository.class);
    private final PersonService personService = mock(PersonService.class);

    private final InterventionService interventionService = new InterventionService(interventionRepository, phaseRepository, personService);
    private Person person;

    @BeforeEach
    void setUp() {
    }
}
