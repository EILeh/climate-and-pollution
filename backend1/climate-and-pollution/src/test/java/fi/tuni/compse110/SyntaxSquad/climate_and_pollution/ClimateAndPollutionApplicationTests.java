package fi.tuni.compse110.SyntaxSquad.climate_and_pollution;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This test class verifies that the Spring Boot application context loads successfully.
 *
 * Key Points:
 * - The `contextLoads` test ensures that all necessary beans and configurations are correctly loaded
 *   into the application context.
 * - If the application context fails to load, this test will fail, indicating a configuration issue
 *   (e.g., missing dependencies, misconfigured beans, or incorrect application properties).
 */

@SpringBootTest
class ClimateAndPollutionApplicationTests {

	@Test
	void contextLoads() {
	}

}
