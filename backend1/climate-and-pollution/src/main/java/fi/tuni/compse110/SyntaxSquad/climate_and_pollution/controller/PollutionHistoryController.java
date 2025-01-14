package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.YearlyAirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OpenWeatherAirPollutionHistoryService;


@RestController
@RequestMapping("/api")
public class PollutionHistoryController {
    
    @Autowired
    private OpenWeatherAirPollutionHistoryService openWeatherService;

    @GetMapping("/pollution-history")
    public List<YearlyAirPollutionData> getAirPollutionHistory(@RequestParam String city) {
        return openWeatherService.getAirPollutionHistoryData(city);
    }
}
