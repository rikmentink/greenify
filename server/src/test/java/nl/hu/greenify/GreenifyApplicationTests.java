package nl.hu.greenify;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.Test;

@SpringBootTest
@ContextConfiguration(classes = {GreenifyApplicationTests.TestConfig.class})
class GreenifyApplicationTests {

	@Test
	void someTest() {
	}

	@Configuration
	static class TestConfig {
		// Define any beans needed for test here
	}
}