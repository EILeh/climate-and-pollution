package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherAirPollutionClient", url = "https://api.openweathermap.org")
public interface OpenWeatherAirPollutionClient {

    @GetMapping("/data/2.5/air_pollution")
    AirPollutionData getAirPollutionData(
        @RequestParam("lat") String latitude,
        @RequestParam("lon") String longitude,
        @RequestParam("appid") String apiKey
    );
}
