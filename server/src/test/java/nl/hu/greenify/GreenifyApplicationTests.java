package nl.hu.greenify;

import domain.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {GreenifyApplicationTests.TestConfig.class})
class GreenifyApplicationTests {

	@Test
	void toStringTest() { //for PITest purposes, you can remove this
		User user = new User("John", "Doe", "test@gmail.com");
		assertEquals("User{firstName='John', lastName='Doe', email='test@gmail.com'}", user.toString());
	}

	@Configuration
	static class TestConfig {
		// Define any beans needed for test here
	}
}