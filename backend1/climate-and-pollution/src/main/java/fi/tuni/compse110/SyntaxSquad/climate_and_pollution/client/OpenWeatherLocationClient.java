package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.LocationData;

import java.util.List;

/**
 * Feign client for the location API
 */
@FeignClient(name = "openWeatherLocationClient", url = "https://api.openweathermap.org")
public interface OpenWeatherLocationClient {

    @GetMapping("/geo/1.0/direct")
    List<LocationData> getLocationData(@RequestParam("q") String cityName, 
                                       @RequestParam("limit") int limit,
                                       @RequestParam("appid") String apiKey);
}
