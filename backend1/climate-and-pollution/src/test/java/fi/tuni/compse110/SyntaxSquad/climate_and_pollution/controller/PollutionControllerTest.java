
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData.PollutionInfo;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData.Coord;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionData.Components;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OpenWeatherAirPollutionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * /**
 * This test class verifies the behavior of the PollutionController,
 * which provides endpoints for retrieving air pollution data by city.
 *
 * Key Points:
 * - The `@WebMvcTest` annotation is used to load only the controller layer of the application,
 *   ensuring that the test focuses solely on the behavior of the controller.
 * - The `MockMvc` object is used to simulate HTTP requests and validate the responses.
 * - The `@MockBean` annotation is used to mock the `OpenWeatherAirPollutionService`,
 *   simulating service-layer behavior for air pollution data retrieval.
 *
 * Test:
 * 1. `testGetAirPollutionByCity` - Verifies the `/api/pollution` endpoint by:
 *    - Checking the HTTP status code (200 OK).
 *    - Validating the JSON response structure, including city, country, coordinates, and pollutant components.
 *    - Ensuring the controller interacts correctly with the mocked service (`OpenWeatherAirPollutionService`).
 * 
 * This test is done with help of ChatGPT.
 * 
 */
@WebMvcTest(PollutionController.class)
class PollutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenWeatherAirPollutionService openWeatherService;

    @Test
    void testGetAirPollutionByCity() throws Exception {
        // Arrange
        String city = "Helsinki";

        // Mock data for AirPollutionData
        AirPollutionData mockData = new AirPollutionData();
        mockData.setCity(city);
        mockData.setCountry("FI");

        Coord coord = new Coord();
        coord.setLon(24.9354);
        coord.setLat(60.1695);
        mockData.setCoord(coord);

        Components components = new Components();
        components.setCo(0.5);
        components.setNo(0.2);
        components.setNo2(0.3);
        components.setO3(0.4);
        components.setSo2(0.1);
        components.setPm2_5(10.5);
        components.setPm10(15.5);
        components.setNh3(0.05);

        PollutionInfo pollutionInfo = new PollutionInfo();
        pollutionInfo.setComponents(components);
        pollutionInfo.setDt(1633036800L); // Example timestamp

        mockData.setList(Collections.singletonList(pollutionInfo));

        when(openWeatherService.getAirPollutionDataByCity(city)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/pollution")
                .param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Helsinki"))
                .andExpect(jsonPath("$.country").value("FI"))
                .andExpect(jsonPath("$.coord.lon").value(24.9354))
                .andExpect(jsonPath("$.coord.lat").value(60.1695))
                .andExpect(jsonPath("$.list[0].components.co").value(0.5))
                .andExpect(jsonPath("$.list[0].components.no").value(0.2))
                .andExpect(jsonPath("$.list[0].components.no2").value(0.3))
                .andExpect(jsonPath("$.list[0].components.o3").value(0.4))
                .andExpect(jsonPath("$.list[0].components.so2").value(0.1))
                .andExpect(jsonPath("$.list[0].components.pm2_5").value(10.5))
                .andExpect(jsonPath("$.list[0].components.pm10").value(15.5))
                .andExpect(jsonPath("$.list[0].components.nh3").value(0.05));

        verify(openWeatherService, times(1)).getAirPollutionDataByCity(city);
    }
}