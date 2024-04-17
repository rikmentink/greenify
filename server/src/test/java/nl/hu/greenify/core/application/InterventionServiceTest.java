package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.InterventionRepository;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.data.PersonRepository;
import nl.hu.greenify.core.data.PhaseRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InterventionServiceTest {
    private final InterventionRepository interventionRepository = mock(InterventionRepository.class);
    private final PhaseRepository phaseRepository = mock(PhaseRepository.class);
    private final InterventionService interventionService = new InterventionService(interventionRepository, phaseRepository, personRepository);
    private Person person;

    @BeforeEach
    void setUp() {
    }
}
