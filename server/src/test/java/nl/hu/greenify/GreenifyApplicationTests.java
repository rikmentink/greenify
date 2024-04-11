package nl.hu.greenify;

import domain.User;
import domain.factor.Factor;
import domain.factor.Subfactor;
import domain.interfaces.IFactor;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
		IFactor factor = new Factor(0L, "", 0, new ArrayList<>());
		assertEquals(0, factor.getNumber());

		Subfactor subfactor = new Subfactor(0L, "", 0, true, factor);
		assertEquals(0, subfactor.getNumber());
		assertEquals(true, subfactor.isSupportingFactor());
	}
}