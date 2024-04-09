package nl.hu.domain;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("User should have a valid email")
    void userValidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new User("John", "Doe", "JohnDoe"));
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

}
