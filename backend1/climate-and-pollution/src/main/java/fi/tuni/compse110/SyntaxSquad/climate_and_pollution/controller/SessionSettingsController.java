package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.SessionSettingsService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session-settings")
public class SessionSettingsController {

    @Autowired
    private SessionSettingsService settingsService;

    // Save session settings (overwrites existing settings)
    @PostMapping("/{nameSpace}")
    public ResponseEntity<Void> saveSettings(@PathVariable String nameSpace, @RequestBody Map<String, Object> settings) {
        settingsService.saveSettings(nameSpace, settings);
        return ResponseEntity.ok().build();
    }

    // Load session settings (returns empty if no settings exist)
    @GetMapping("/{nameSpace}")
    public ResponseEntity<Map<String, Object>> loadSettings(@PathVariable String nameSpace) {
    Map<String, Object> settings = settingsService.loadSettings(nameSpace); // Fetches information according to nameSpace
    return ResponseEntity.ok(settings);
}
}