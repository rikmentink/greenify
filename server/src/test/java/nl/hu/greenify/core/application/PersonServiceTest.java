package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.PersonRepository;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.mock;

@DisplayName("Person Service Test")
public class PersonServiceTest {
    private final PersonRepository personRepository = mock(PersonRepository.class);
}
