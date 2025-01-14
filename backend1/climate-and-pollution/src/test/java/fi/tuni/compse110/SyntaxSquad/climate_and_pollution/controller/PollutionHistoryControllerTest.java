
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.YearlyAirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OpenWeatherAirPollutionHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * /**
 * This test class verifies the behavior of the PollutionHistoryController,
 * which provides endpoints for retrieving historical air pollution data by city.
 *
 * Key Points:
 * - The `@WebMvcTest` annotation is used to load only the controller layer of the application,
 *   ensuring that the test focuses solely on the behavior of the controller.
 * - The `MockMvc` object is used to simulate HTTP requests and validate the responses.
 * - The `@MockBean` annotation is used to mock the `OpenWeatherAirPollutionHistoryService`,
 *   simulating service-layer behavior for retrieving historical air pollution data.
 *
 * Test:
 * 1. `testGetAirPollutionHistory` - Verifies the `/api/pollution-history` endpoint by:
 *    - Checking the HTTP status code (200 OK).
 *    - Validating the JSON response structure, including city name, coordinates, year,
 *      and averaged air pollution component values (e.g., pm2_5, pm10).
 *    - Ensuring the controller interacts correctly with the mocked service (`OpenWeatherAirPollutionHistoryService`).
 * 
 * This test is done with help of ChatGPT.
 * 
 */

@WebMvcTest(PollutionHistoryController.class)
class PollutionHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenWeatherAirPollutionHistoryService openWeatherService;

    @Test
    void testGetAirPollutionHistory() throws Exception {
        // Arrange
        String city = "Helsinki";

        Map<String, Double> averagedComponents1 = new HashMap<>();
        averagedComponents1.put("pm2_5", 10.5);
        averagedComponents1.put("pm10", 20.5);

        Map<String, Double> averagedComponents2 = new HashMap<>();
        averagedComponents2.put("pm2_5", 15.0);
        averagedComponents2.put("pm10", 25.0);

        YearlyAirPollutionData data1 = new YearlyAirPollutionData();
        data1.setCityName(city);
        data1.setLat(60.1695);
        data1.setLon(24.9354);
        data1.setYear(2020);
        data1.setAveragedComponents(averagedComponents1);

        YearlyAirPollutionData data2 = new YearlyAirPollutionData();
        data2.setCityName(city);
        data2.setLat(60.1695);
        data2.setLon(24.9354);
        data2.setYear(2021);
        data2.setAveragedComponents(averagedComponents2);

        List<YearlyAirPollutionData> mockData = Arrays.asList(data1, data2);

        when(openWeatherService.getAirPollutionHistoryData(city)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/pollution-history")
                .param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("Helsinki"))
                .andExpect(jsonPath("$[0].lat").value(60.1695))
                .andExpect(jsonPath("$[0].lon").value(24.9354))
                .andExpect(jsonPath("$[0].year").value(2020))
                .andExpect(jsonPath("$[0].averagedComponents.pm2_5").value(10.5))
                .andExpect(jsonPath("$[0].averagedComponents.pm10").value(20.5))
                .andExpect(jsonPath("$[1].year").value(2021))
                .andExpect(jsonPath("$[1].averagedComponents.pm2_5").value(15.0))
                .andExpect(jsonPath("$[1].averagedComponents.pm10").value(25.0));

        verify(openWeatherService, times(1)).getAirPollutionHistoryData(city);
    }
}