
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOIndicatorData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.WHOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * /**
 * This test class verifies the behavior of the HealthDataController,
 * which provides endpoints for retrieving WHO-related health indicator data.
 *
 * Key Points:
 * - The `@WebMvcTest` annotation is used to load only the controller layer of the application,
 *   ensuring that the test focuses solely on the behavior of the controller.
 * - The `MockMvc` object is used to simulate HTTP requests and validate the responses.
 * - The `@MockBean` annotation is used to mock the `WHOService`, simulating service-layer behavior.
 * - Mock data is created in the `setUp` method to represent the expected response from the service layer.
 *
 * Tests:
 * 1. `testGetAir15Data` - Verifies the `/api/air15` endpoint, which retrieves data for AIR_15 indicator.
 * 2. `testGetAir43Data` - Verifies the `/api/air43` endpoint, which retrieves data for AIR_43 indicator.
 * 
 * This test is done with help of ChatGPT.
 * 
 */

@WebMvcTest(HealthDataController.class)
class HealthDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WHOService whoService;

    private List<WHOIndicatorData> mockData;

    @BeforeEach
    void setUp() {
        // Luodaan mock-data käyttäen oletuskonstruktoria ja settejä
        WHOIndicatorData data1 = new WHOIndicatorData();
        data1.setSpatialDim("FIN");
        data1.setTimeDim(2020);
        data1.setNumericValue(10.5);

        WHOIndicatorData data2 = new WHOIndicatorData();
        data2.setSpatialDim("FIN");
        data2.setTimeDim(2021);
        data2.setNumericValue(11.0);

        mockData = Arrays.asList(data1, data2);
    }
    
    @Test
    void testGetAir15Data() throws Exception {
        // Arrange
        String country = "FIN";
        String gtyear = "2000";
        String ltyear = "2020";

        when(whoService.getFilteredIndicatorData(eq("AIR_15"), anyMap())).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/air15")
                .param("country", country)
                .param("gtyear", gtyear)
                .param("ltyear", ltyear))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].SpatialDim").value("FIN")) // Tarkistetaan SpatialDim
                .andExpect(jsonPath("$[0].TimeDim").value(2020)) // Tarkistetaan TimeDim
                .andExpect(jsonPath("$[0].NumericValue").value(10.5)); // Tarkistetaan NumericValue

        verify(whoService, times(1)).getFilteredIndicatorData(eq("AIR_15"), anyMap());
    }

    @Test
    void testGetAir43Data() throws Exception {
        // Arrange
        String country = "FIN";
        String gtyear = "1990";
        String ltyear = "2020";

        when(whoService.getFilteredIndicatorData(eq("AIR_43"), anyMap())).thenReturn(mockData);

        // Act & Assert
        mockMvc.perform(get("/api/air43")
                .param("country", country)
                .param("gtyear", gtyear)
                .param("ltyear", ltyear))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].SpatialDim").value("FIN")) // Tarkistetaan SpatialDim
                .andExpect(jsonPath("$[0].TimeDim").value(2020)) // Tarkistetaan TimeDim
                .andExpect(jsonPath("$[0].NumericValue").value(10.5)); // Tarkistetaan NumericValue

        verify(whoService, times(1)).getFilteredIndicatorData(eq("AIR_43"), anyMap());
    }
    
}