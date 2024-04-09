package nl.hu.domain;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("User Domain Test")
public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "JohnDoe@gmail.com");
    }

    @Test
    @DisplayName("User should have a first name")
    void userFirstName() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, "Doe", "JohnDoe@gmail.com"));
    }

    @Test
    @DisplayName("User should have a last name")
    void userLastName() {
        assertThrows(IllegalArgumentException.class, () -> new User("John", null, "JohnDoe@Gmail.com"));
    }

    @Test
    @DisplayName("User should have an email")
    void userEmail() {
        assertThrows(IllegalArgumentException.class, () -> new User("John", "Doe", null));
    }

    @Test
    @DisplayName("User should be able to add a valid intervention")
    void userAddIntervention() {
        user.addIntervention("Garden", "Watering the plants");
    }

    @Test
    @DisplayName("User should not be able to add an invalid intervention")
    void userAddInvalidIntervention() {
        assertThrows(IllegalArgumentException.class, () -> user.addIntervention(null, "Watering the plants"));
    }

    @ParameterizedTest
    @DisplayName("User should have a valid email")
    @MethodSource("emailProvider")
    void userValidEmailParameterized(String email, boolean expected) {
        if (!expected) {
            assertThrows(IllegalArgumentException.class, () -> new User("John", "Doe", email));
        } else {
            new User("John", "Doe", email);
        }
    }

    private static Collection<Object[]> emailProvider() {
        return Arrays.asList(new Object[][]{
                {"example@example.com", true},
                {"john.doe@example.com", true},
                {"john.doe123@example.com", true},
                {"invalid-email.com", false},
                {"john@example", false},
                {"john@example.", false},
                {"john.example.com", false},
                {null, false},
                {"", false}
        });
    }

}
