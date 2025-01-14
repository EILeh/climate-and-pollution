
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * /**
 * This test class verifies the behavior of the SessionSettingsService,
 * which provides functionality for saving and loading session settings
 * across different namespaces.
 *
 * Key Points:
 * - The service uses a single JSON file (`session-settings.json`) to store
 *   session settings for different namespaces.
 * - Each test ensures that the service behaves correctly in various scenarios,
 *   such as saving, overwriting, and loading settings.
 *
 * Tests:
 * 1. `testSaveAndLoadSettingsForNamespace`:
 *    - Verifies that the service can save and load settings for a specific namespace.
 *    - Ensures that the saved settings match the loaded ones.
 * 2. `testSaveAndLoadMultipleNamespaces`:
 *    - Verifies that the service can handle multiple namespaces independently.
 *    - Ensures that settings for one namespace do not affect another.
 * 3. `testLoadSettingsWhenFileDoesNotExist`:
 *    - Ensures that the service returns an empty map when no settings file exists.
 *    - Validates the service's behavior in a clean state.
 * 4. `testSaveSettingsOverwriteNamespace`:
 *    - Verifies that saving settings for the same namespace overwrites the previous settings.
 *    - Ensures that the most recent settings are loaded correctly.
 * 
 * This test is done with help of ChatGPT.
 */

class SessionSettingsServiceTest {

    private SessionSettingsService service;

    @BeforeEach
    void setUp() {
        // Initialize the service
        service = new SessionSettingsService();

        // Ensure the file is cleaned up before each test
        File file = new File("session-settings.json");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveAndLoadSettingsForNamespace() {
        // Create a map to save under a namespace
        Map<String, Object> settings = new HashMap<>();
        settings.put("theme", "dark");
        settings.put("fontSize", 14);

        String namespace = "chart";

        // Save the settings
        service.saveSettings(namespace, settings);

        // Load the settings for the namespace
        Map<String, Object> loadedSettings = service.loadSettings(namespace);

        // Assert that the loaded settings match the saved ones
        assertEquals(settings, loadedSettings);
    }

    @Test
    void testSaveAndLoadMultipleNamespaces() {
        // Create maps for different namespaces
        Map<String, Object> chartSettings = new HashMap<>();
        chartSettings.put("theme", "dark");

        Map<String, Object> mapSettings = new HashMap<>();
        mapSettings.put("zoom", 5);

        // Save settings under different namespaces
        service.saveSettings("chart", chartSettings);
        service.saveSettings("map", mapSettings);

        // Load settings for each namespace
        Map<String, Object> loadedChartSettings = service.loadSettings("chart");
        Map<String, Object> loadedMapSettings = service.loadSettings("map");

        // Assert that each namespace has the correct settings
        assertEquals(chartSettings, loadedChartSettings);
        assertEquals(mapSettings, loadedMapSettings);
    }

    @Test
    void testLoadSettingsWhenFileDoesNotExist() {
        // Attempt to load settings for a namespace when the file does not exist
        Map<String, Object> loadedSettings = service.loadSettings("chart");

        // Assert that the returned map is empty
        assertTrue(loadedSettings.isEmpty());
    }

    @Test
    void testSaveSettingsOverwriteNamespace() {
        // Save initial settings under a namespace
        Map<String, Object> initialSettings = new HashMap<>();
        initialSettings.put("theme", "light");
        service.saveSettings("chart", initialSettings);

        // Overwrite the settings for the same namespace
        Map<String, Object> newSettings = new HashMap<>();
        newSettings.put("theme", "dark");
        service.saveSettings("chart", newSettings);

        // Load the settings for the namespace
        Map<String, Object> loadedSettings = service.loadSettings("chart");

        // Assert that the loaded settings match the new ones
        assertEquals(newSettings, loadedSettings);
    }
}