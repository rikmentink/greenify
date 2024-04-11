package nl.hu.greenify;

import nl.hu.greenify.domain.User;
import nl.hu.greenify.domain.factor.Subfactor;
import nl.hu.greenify.domain.factor.Factor;
import nl.hu.greenify.domain.interfaces.IFactor;
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
		IFactor IFactor = new Factor(List.of()); //Parent, is also referenced thru Factor class
		assertEquals(0, IFactor.number); //Check if the number is 0, according to the interface

		Subfactor subfactor = new Subfactor(IFactor);
		assertEquals(0, subfactor.getParentIFactor().number); //Check if the number is 0, according to the interface
	}
}