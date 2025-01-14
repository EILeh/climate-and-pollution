
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.ClimateAndPollutionApplication;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.GlobalExceptionHandler;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller.TestErrorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 
 * This test class verifies the behavior of the GlobalExceptionHandler, 
 * which is responsible for handling exceptions thrown in the application and 
 * providing meaningful HTTP responses to the client.
 *
 * Key Points:
 * - Each test method simulates a specific exception being thrown, such as WHOServiceException, 
 *   OECDServiceException, OpenWeatherServiceException, or a general RuntimeException.
 * - The test ensures that the exception handler returns the correct HTTP status code (500 Internal Server Error) 
 *   and the expected error message in the response body.
 * - The `TestErrorController` is used as a mock controller to simulate different exceptions, 
 *   allowing us to validate the behavior of the exception handler without relying on actual application logic.
 * 
 * This test is done with help of ChatGPT.
 */


@SpringBootTest(classes = {ClimateAndPollutionApplication.class, TestErrorController.class}) // Lisää TestErrorController
@AutoConfigureMockMvc
@Import(GlobalExceptionHandler.class) // Lisää GlobalExceptionHandler testikontekstiin
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testWHOServiceExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/trigger-error")
                .param("exceptionType", "WHOServiceException") // Simuloi WHOServiceException
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("WHOService Error: WHO service failed"));
    }

    @Test
    void testOECDServiceExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/trigger-error")
                .param("exceptionType", "OECDServiceException") // Simuloi OECDServiceException
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("OECDService Error: OECD service failed"));
    }

    @Test
    void testOpenWeatherServiceExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/trigger-error")
                .param("exceptionType", "OpenWeatherServiceException") // Simuloi OpenWeatherServiceException
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("OpenWeatherAirPollutionService Error: OpenWeather service failed"));
    }

    @Test
    void testGeneralExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/trigger-error")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error: Unexpected error occurred"));
    }
}