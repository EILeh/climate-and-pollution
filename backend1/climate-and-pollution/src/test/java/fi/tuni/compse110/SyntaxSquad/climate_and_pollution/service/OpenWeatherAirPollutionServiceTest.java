
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherAirPollutionClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherLocationClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.LocationData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * /**
 * This test class verifies the behavior of the OpenWeatherAirPollutionService,
 * which retrieves air pollution data based on a city name by interacting with
 * OpenWeather's location and air pollution APIs.
 *
 * Key Points:
 * - The `@Mock` annotation is used to mock dependencies (`OpenWeatherAirPollutionClient` and
 *   `OpenWeatherLocationClient`), which simulate interactions with external APIs.
 * - The `@InjectMocks` annotation injects these mocks into the service being tested.
 * - Reflection is used in the `setUp` method to set the `apiKey` field, enabling the service
 *   to run without relying on external configuration.
 *
 * Tests:
 * 1. `testGetAirPollutionDataByCity_Success`:
 *    - Verifies that the service retrieves and processes air pollution data for a valid city.
 *    - Mocks the location and air pollution API responses and validates the resulting data structure.
 *    - Ensures that both `locationClient` and `airPollutionClient` are called with the correct parameters.
 * 2. `testGetAirPollutionDataByCity_CityNotFound`:
 *    - Verifies that the service throws a `RuntimeException` when no location data is found for the given city.
 *    - Ensures that `airPollutionClient` is not called in this scenario.
 * 
 * This test is done with help of ChatGPT.
 * 
 */
class OpenWeatherAirPollutionServiceTest {

    @Mock
    private OpenWeatherAirPollutionClient airPollutionClient;

    @Mock
    private OpenWeatherLocationClient locationClient;

    @InjectMocks
    private OpenWeatherAirPollutionService service;

    private static final String API_KEY = "test-api-key";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        java.lang.reflect.Field apiKeyField = OpenWeatherAirPollutionService.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(service, API_KEY); 
    }

    @Test
    void testGetAirPollutionDataByCity_Success() {
        // Arrange
        String cityName = "Helsinki";

        // Mockataan locationClient-vastaus
        LocationData locationData = new LocationData();
        locationData.setLat(60.192059);
        locationData.setLon(24.945831);
        locationData.setName(cityName);
        locationData.setCountry("FI");

        when(locationClient.getLocationData(cityName, 5, API_KEY)).thenReturn(List.of(locationData));

        // Mockataan airPollutionClient-vastaus
        AirPollutionData pollutionData = new AirPollutionData();
        when(airPollutionClient.getAirPollutionData("60.192059", "24.945831", API_KEY)).thenReturn(pollutionData);

        // Act
        AirPollutionData result = service.getAirPollutionDataByCity(cityName);

        // Assert
        assertNotNull(result);
        assertEquals(cityName, result.getCity());
        assertEquals("FI", result.getCountry());
        verify(locationClient, times(1)).getLocationData(cityName, 5, API_KEY);
        verify(airPollutionClient, times(1)).getAirPollutionData("60.192059", "24.945831", API_KEY);
    }

    @Test
    void testGetAirPollutionDataByCity_CityNotFound() {
        // Arrange
        String cityName = "UnknownCity";
        when(locationClient.getLocationData(cityName, 5, API_KEY)).thenReturn(List.of());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getAirPollutionDataByCity(cityName);
        });

        assertEquals("City not found: " + cityName, exception.getMessage());
        verify(locationClient, times(1)).getLocationData(cityName, 5, API_KEY);
        verifyNoInteractions(airPollutionClient); // Varmistetaan, että airPollutionClientiä ei kutsuttu.
    }
}
