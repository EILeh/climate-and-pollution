package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.AirPollutionHistoryData;

@FeignClient(name = "openWeatherAirPollutionHistoryClient", url = "https://api.openweathermap.org")
public interface OpenWeatherAirPollutionHistoryClient {

    @GetMapping("/data/2.5/air_pollution/history")
    AirPollutionHistoryData getAirPollutionHistoryData(
        @RequestParam("lat") String latitude,
        @RequestParam("lon") String longitude,
        @RequestParam("start") String startTime,
        @RequestParam("end") String endTime,
        @RequestParam("appid") String apiKey
    );
}
