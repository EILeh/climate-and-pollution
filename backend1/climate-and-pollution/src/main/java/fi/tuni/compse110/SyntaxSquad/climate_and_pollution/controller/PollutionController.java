package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OpenWeatherAirPollutionService;

/**
 * This class offers data for frontend!
 * 
 */

@RestController
@RequestMapping("/api")
public class PollutionController {

    @Autowired
    private OpenWeatherAirPollutionService openWeatherService;

    @GetMapping("/pollution")
    public AirPollutionData getAirPollutionByCity(@RequestParam String city) {
        return openWeatherService.getAirPollutionDataByCity(city);
    }
}
