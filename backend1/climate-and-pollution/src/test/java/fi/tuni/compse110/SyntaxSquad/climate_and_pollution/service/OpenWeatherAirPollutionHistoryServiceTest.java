
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherAirPollutionHistoryClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherLocationClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.*;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.AirPollutionHistoryData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.Components;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.DataPoint;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.YearlyAirPollutionData;


import java.util.*;
/**
 * /**
 * This test class verifies the behavior of the OpenWeatherAirPollutionHistoryService,
 * which retrieves and processes historical air pollution data based on city input.
 *
 * Key Points:
 * - The `@Mock` annotation is used to mock dependencies such as `OpenWeatherAirPollutionHistoryClient`
 *   and `OpenWeatherLocationClient`, which simulate external API calls for location and air pollution data.
 * - The `@InjectMocks` annotation injects the mocked dependencies into the service being tested.
 * - Reflection is used in the `setUp` method to set the `apiKey` field of the service, ensuring that the
 *   test can execute without relying on external configurations.
 *
 * Tests:
 * 1. `testGetAirPollutionHistoryData`:
 *    - Verifies the service's ability to retrieve and process historical air pollution data for a valid city.
 *    - Mocks the location and air pollution data responses to simulate external API behavior.
 *    - Validates the structure and values of the processed `YearlyAirPollutionData` objects.
 * 2. `testCityNotFound`:
 *    - Verifies that the service throws a `RuntimeException` when the requested city is not found.
 *    - Ensures that an appropriate error message is returned.
 * 
 * This test is done with help of ChatGPT.
 * 
 */

class OpenWeatherAirPollutionHistoryServiceTest {

    @Mock
    private OpenWeatherAirPollutionHistoryClient historyClient;

    @Mock
    private OpenWeatherLocationClient locationClient;

    @InjectMocks
    private OpenWeatherAirPollutionHistoryService service;

    @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);

        try {
            // Aseta apiKey-kentän arvo manuaalisesti Java Reflection API:lla
            Field apiKeyField = service.getClass().getDeclaredField("apiKey");
            apiKeyField.setAccessible(true);
            apiKeyField.set(service, "mockApiKey");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field via reflection", e);
        }
    }
    

    @Test
    void testGetAirPollutionHistoryData() {
        // Mock location data for Helsinki
        Map<String, String> localNames = Map.of("en", "Helsinki");
        LocationData mockLocation = new LocationData("Helsinki", localNames, 60.1699, 24.9384, "FI");
        List<LocationData> mockLocations = List.of(mockLocation);

        // Mock locationClient response
        when(locationClient.getLocationData(eq("Helsinki"), eq(5), anyString()))
            .thenReturn(mockLocations);

        // Mock air pollution data
        List<DataPoint> mockDataPoints = List.of(
            createDataPoint(1609459200, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0), // Jan 1, 2021
            createDataPoint(1640995200, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)  // Jan 1, 2022
        );
        AirPollutionHistoryData mockPollutionData = new AirPollutionHistoryData();
        mockPollutionData.setList(mockDataPoints);

        // Mock historyClient response
        when(historyClient.getAirPollutionHistoryData(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(mockPollutionData);

        // Call the method under test
        List<YearlyAirPollutionData> results = service.getAirPollutionHistoryData("Helsinki");

        // Validate results
        assertNotNull(results);
        assertEquals(2, results.size());

        // Validate first year's data
        YearlyAirPollutionData firstYearData = results.get(0);
        assertEquals("Helsinki", firstYearData.getCityName());
        assertEquals(2021, firstYearData.getYear());
        assertEquals(60.1699, firstYearData.getLat());
        assertEquals(24.9384, firstYearData.getLon());
        assertEquals(1.0, firstYearData.getAveragedComponents().get("co"));

        // Validate second year's data
        YearlyAirPollutionData secondYearData = results.get(1);
        assertEquals("Helsinki", secondYearData.getCityName());
        assertEquals(2022, secondYearData.getYear());
        assertEquals(2.0, secondYearData.getAveragedComponents().get("co"));
    }

    @Test
    void testCityNotFound() {
        // Mock empty location data
        when(locationClient.getLocationData(eq("NonExistentCity"), eq(5), anyString()))
            .thenReturn(Collections.emptyList());

        // Call the method under test and expect an exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getAirPollutionHistoryData("NonExistentCity");
        });

        // Validate exception message
        assertEquals("City not found: NonExistentCity", exception.getMessage());
    }

    private DataPoint createDataPoint(long timestamp, double co, double no, double no2, double o3, double so2, double pm2_5, double pm10, double nh3) {
        DataPoint dataPoint = new DataPoint();
        dataPoint.setDt(timestamp);

        Components components = new Components();
        components.setCo(co);
        components.setNo(no);
        components.setNo2(no2);
        components.setO3(o3);
        components.setSo2(so2);
        components.setPm2_5(pm2_5);
        components.setPm10(pm10);
        components.setNh3(nh3);

        dataPoint.setComponents(components);
        return dataPoint;
    }
}