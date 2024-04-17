package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.data.PersonRepository;

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
    private PersonRepository personRepository;
    private Person person;

    @BeforeEach
    void setUp() {
    }
}
