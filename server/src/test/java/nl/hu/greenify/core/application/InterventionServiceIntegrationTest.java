package nl.hu.greenify.core.application;

import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.data.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
