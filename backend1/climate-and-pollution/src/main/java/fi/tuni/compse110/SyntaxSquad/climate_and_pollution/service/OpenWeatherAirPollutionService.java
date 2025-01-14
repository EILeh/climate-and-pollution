
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherAirPollutionClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherLocationClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.LocationData;

/**
 * This class handles communication with the OpenWeather API,
 * retrieving weather and air quality data based on the city name or
 * geographical coordinates. 
 * (first location data by city name, then the air quality data with the location)
 * 
 */
@Service
public class OpenWeatherAirPollutionService {

    @Autowired
    private OpenWeatherAirPollutionClient openWeatherAirPollutionClient;

    @Autowired
    private OpenWeatherLocationClient openWeatherLocationClient;

    @Value("${openweather.api.key}")
     String apiKey;

    // Fetch pollution data by city name
    public AirPollutionData getAirPollutionDataByCity(String cityName) {
        
        // Step 1: Fetch coordinates for the city using the location API
        List<LocationData> locations = openWeatherLocationClient.getLocationData(cityName, 5, apiKey);

        if (locations.isEmpty()) {
            throw new RuntimeException("City not found: " + cityName);
        }

        LocationData location = locations.get(0); // Get the first result

        // Step 2: Fetch air pollution data using the fetched coordinates
        AirPollutionData pollutionData = openWeatherAirPollutionClient.getAirPollutionData(
            String.valueOf(location.getLat()),
            String.valueOf(location.getLon()),
            apiKey
        );

        // Step 3: Set location details (city and country) on the pollutionData object
        pollutionData.setCity(location.getName());
        pollutionData.setCountry(location.getCountry());

        return pollutionData;
    }
}
