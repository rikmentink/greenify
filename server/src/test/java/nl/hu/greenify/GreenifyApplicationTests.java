package nl.hu.greenify;

import domain.User;
import domain.interfaces.IFactor;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GreenifyApplicationTests {

	@Test
	void toStringTest() { //for PITest purposes, you can remove this
		User user = new User("John", "Doe", "test@gmail.com");
		assertEquals("User{firstName='John', lastName='Doe', email='test@gmail.com'}", user.toString());

	}

	@Test
	void interfaceTest() {
		IFactor IFactor = new domain.factor.Factor(List.of());
		assertEquals(0, IFactor.number);
		System.out.println(IFactor);
	}
}