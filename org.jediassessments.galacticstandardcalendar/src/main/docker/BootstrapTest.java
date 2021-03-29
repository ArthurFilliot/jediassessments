package deploy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BootstrapTest {

	@Test
	public void test() {
		assertTrue(1==1);
	}
}
