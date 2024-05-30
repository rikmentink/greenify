package nl.hu.greenify.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@DisplayName("Person Domain Test")
public class PersonTest {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "John", "Doe", "JohnDoe@gmail.com", new ArrayList<>());
    }

    @Test
    @DisplayName("User should have a first name")
    void userFirstName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Person(2L, null, "Doe", "JohnDoe@gmail.com", new ArrayList<>()));
    }

    @Test
    @DisplayName("User should have a last name")
    void userLastName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Person(2L, "John", null, "JohnDoe@Gmail.com", new ArrayList<>()));
    }

    @Test
    @DisplayName("User should have an email")
    void userEmail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Person(2L, "John", "Doe", null, new ArrayList<>()));
    }

    @ParameterizedTest
    @DisplayName("User should have a valid email")
    @MethodSource("emailProvider")
    void userValidEmailParameterized(String email, boolean expected) {
        if (!expected) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> new Person(2L, "John", "Doe", email, new ArrayList<>()));
        } else {
            new Person(2L, "John", "Doe", email, new ArrayList<>());
        }
    }

    private static Collection<Object[]> emailProvider() {
        return Arrays.asList(new Object[][]{
                {"example@example.com", true},
                {"john.doe@example.com", true},
                {"JoHN.doe123@example.com", true},
                {"invalid-email.com", false},
                {"joFSn@example", false},
                {"john@example.", false},
                {"joSn.example.com", false},
                {null, false},
                {"", false}
        });
    }
}
