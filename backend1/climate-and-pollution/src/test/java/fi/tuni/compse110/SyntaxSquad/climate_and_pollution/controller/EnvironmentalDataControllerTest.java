
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.OECDData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OECDService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 *
 * This test class verifies the behavior of the EnvironmentalDataController,
 * which provides endpoints for retrieving OECD-related environmental data.
 *
 * Key Points:
 * - The `@WebMvcTest` annotation is used to load only the controller layer of the application,
 *   ensuring that the test focuses solely on the behavior of the controller.
 * - The `MockMvc` object is used to perform HTTP requests and verify the responses.
 * - The `@MockBean` annotation is used to mock the `OECDService`, which simulates service-layer behavior.
 * - Sample data is created in the `setUp` method to represent the expected response from the mocked service.
 *
 * Tests:
 * 1. `testGetMortalityData` - Verifies the `/api/mortality` endpoint returns the correct data.
 * 2. `testGetXTempData` - Verifies the `/api/xtemp` endpoint returns the correct data.
 * 3. `testGetDroughtData` - Verifies the `/api/drought` endpoint returns the correct data.
 * 
 * This test is done with help of ChatGPT.
 * 
 */

@WebMvcTest(EnvironmentalDataController.class)
class EnvironmentalDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OECDService oecdService;

    private List<OECDData> mockData;

    @BeforeEach
    void setUp() {
        // Mock sample data
        OECDData data1 = new OECDData("FIN", 2020, 10.5); // Finland data
        OECDData data2 = new OECDData("FIN", 2021, 11.0);
        mockData = Arrays.asList(data1, data2);
    }

    @Test
    void testGetMortalityData() throws Exception {
        // Arrange
        String country = "FIN";
        when(oecdService.getMortalityData(country)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/mortality")
                .param("country", country))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].countryCode").value("FIN")) // Tarkistetaan countryCode
                .andExpect(jsonPath("$[0].year").value(2020))
                .andExpect(jsonPath("$[0].value").value(10.5));
        
        verify(oecdService, times(1)).getMortalityData(country);
    }

    @Test
    void testGetXTempData() throws Exception {
        // Arrange
        String country = "FIN";
        when(oecdService.getXTempData(country)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/xtemp")
                .param("country", country))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].countryCode").value("FIN")) // Tarkistetaan countryCode
                .andExpect(jsonPath("$[0].year").value(2020))
                .andExpect(jsonPath("$[0].value").value(10.5));
        
        verify(oecdService, times(1)).getXTempData(country);
    }

    @Test
    void testGetDroughtData() throws Exception {
        // Arrange
        String country = "FIN";
        when(oecdService.getDroughtData(country)).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/drought")
                .param("country", country))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].countryCode").value("FIN")) // Tarkistetaan toinen rivi
                .andExpect(jsonPath("$[1].year").value(2021))
                .andExpect(jsonPath("$[1].value").value(11.0));

        verify(oecdService, times(1)).getDroughtData(country);
    }
}