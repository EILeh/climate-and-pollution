package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionSettingsService {

    private static final String STORAGE_FILE = "session-settings.json"; // Single JSON file
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Save session settings (chart and map)
    public void saveSettings(String nameSpace, Map<String, Object> settings) {
        File file = new File(STORAGE_FILE);
        Map<String, Object> allSettings;

        // Load existing settings if the file exists
        if (file.exists()) {
            try {
                allSettings = objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Failed to load existing settings", e);
            }
        } else {
            allSettings = new HashMap<>();
        }

        // Save chart or map settings separately
        allSettings.put(nameSpace, settings);

        // Save all settings to the file
        try {
            objectMapper.writeValue(file, allSettings);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save session settings", e);
        }
    }

    // Load session settings for a specific nameSpace (chart or map)
    public Map<String, Object> loadSettings(String nameSpace) {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            return new HashMap<>(); // Return an empty map if no data exists
        }

        try {
            // Load all settings from the file
            Map<String, Object> allSettings = objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {});
            // Return only the settings for the requested nameSpace (chart or map)
            Object namespaceData = allSettings.getOrDefault(nameSpace, new HashMap<>());
            return objectMapper.convertValue(namespaceData, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load session settings", e);
        }
    }
}
