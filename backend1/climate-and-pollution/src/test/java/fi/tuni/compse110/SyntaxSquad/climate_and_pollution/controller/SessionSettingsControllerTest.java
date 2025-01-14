
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.SessionSettingsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * /**
 * This test class verifies the behavior of the SessionSettingsController,
 * which provides endpoints for saving and loading user session settings.
 *
 * Key Points:
 * - The `@WebMvcTest` annotation is used to load only the controller layer of the application,
 *   ensuring that the test focuses solely on the behavior of the controller.
 * - The `MockMvc` object is used to simulate HTTP requests and validate the responses.
 * - The `@MockBean` annotation is used to mock the `SessionSettingsService`,
 *   simulating service-layer behavior for managing session settings.
 *
 * Tests:
 * 1. `testSaveSettings` - Verifies the `/api/session-settings/{nameSpace}` (POST) endpoint by:
 *    - Checking that the controller successfully processes a valid JSON payload.
 *    - Ensuring the service's `saveSettings` method is called with the correct parameters.
 * 2. `testLoadSettings` - Verifies the `/api/session-settings/{nameSpace}` (GET) endpoint by:
 *    - Checking that the correct settings are returned as a JSON response.
 *    - Validating the structure and values of the returned JSON object.
 * 3. `testLoadSettingsEmptyResponse` - Verifies that the `/api/session-settings/{nameSpace}` (GET)
 *    endpoint returns an empty JSON object when no settings exist for the given namespace.
 * 
 * This test is done with help of ChatGPT.
 * 
 */
@WebMvcTest(SessionSettingsController.class)
class SessionSettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionSettingsService settingsService;

    @Test
    void testSaveSettings() throws Exception {
        // Arrange
        String nameSpace = "userPreferences";
        String requestBody = "{\"theme\": \"dark\", \"language\": \"en\"}";

        doNothing().when(settingsService).saveSettings(eq(nameSpace), anyMap());

        // Act & Assert
        mockMvc.perform(post("/api/session-settings/{nameSpace}", nameSpace)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        verify(settingsService, times(1)).saveSettings(eq(nameSpace), anyMap());
    }

    @Test
    void testLoadSettings() throws Exception {
        // Arrange
        String nameSpace = "userPreferences";
        Map<String, Object> mockSettings = new HashMap<>();
        mockSettings.put("theme", "dark");
        mockSettings.put("language", "en");

        when(settingsService.loadSettings(nameSpace)).thenReturn(mockSettings);

        // Act & Assert
        mockMvc.perform(get("/api/session-settings/{nameSpace}", nameSpace))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.theme").value("dark"))
                .andExpect(jsonPath("$.language").value("en"));

        verify(settingsService, times(1)).loadSettings(nameSpace);
    }

    @Test
    void testLoadSettingsEmptyResponse() throws Exception {
        // Arrange
        String nameSpace = "nonExistentNamespace";

        when(settingsService.loadSettings(nameSpace)).thenReturn(new HashMap<>());

        // Act & Assert
        mockMvc.perform(get("/api/session-settings/{nameSpace}", nameSpace))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0)); // Empty JSON object

        verify(settingsService, times(1)).loadSettings(nameSpace);
    }
}