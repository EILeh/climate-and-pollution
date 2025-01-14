
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.WHOClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOIndicatorData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOResponseWrapper;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.*;
/**
 * /**
 * This test class verifies the behavior of the WHOService,
 * which is responsible for retrieving and filtering health indicator data from the WHO API.
 *
 * Key Points:
 * - The `@Mock` annotation is used to mock the `WHOClient`, simulating interactions with the WHO API.
 * - The `@InjectMocks` annotation is used to inject the mocked client into the service being tested.
 * - Various scenarios are tested to ensure the service behaves correctly when fetching and filtering data.
 *
 * Tests:
 * 1. `testGetFilteredIndicatorData_Success`:
 *    - Verifies that the service successfully fetches and filters indicator data for a valid request.
 *    - Ensures the data returned matches the expected values, including the indicator code and value.
 * 2. `testGetFilteredIndicatorData_InvalidFilter`:
 *    - Verifies that the service throws a `WHOServiceException` when an invalid filter key is used.
 *    - Ensures that the exception message contains the invalid filter key.
 * 3. `testGetFilteredIndicatorData_ClientThrowsException`:
 *    - Simulates a runtime exception from the client and verifies that the service wraps it in a `WHOServiceException`.
 *    - Ensures that the exception message provides meaningful feedback about the error.
 * 4. `testGetFilteredIndicatorData_NullResponse`:
 *    - Verifies that the service throws a `WHOServiceException` when the client returns a null response.
 *    - Ensures that the exception message indicates the null response issue.
 * 
 * This test is done with help of ChatGPT.
 * 
 */
class WHOServiceTest {

    @Mock
    private WHOClient whoClient;

    @InjectMocks
    private WHOService whoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredIndicatorData_Success() {
        // Arrange
        String indicatorCode = "DIABETES";
        Map<String, String> filters = Map.of("SpatialDim", "FI", "TimeDimGT", "2000");

        WHOIndicatorData data1 = new WHOIndicatorData();
        data1.setIndicatorCode("DIABETES");
        data1.setValue("7.2"); // Arvo asetetaan String-muodossa, koska WHOIndicatorData odottaa String-tyyppiä

        WHOResponseWrapper mockResponse = new WHOResponseWrapper();
        mockResponse.setValue(List.of(data1));

        when(whoClient.getIndicatorData(eq(indicatorCode), anyString())).thenReturn(mockResponse);

        // Act
        List<WHOIndicatorData> result = whoService.getFilteredIndicatorData(indicatorCode, filters);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("DIABETES", result.get(0).getIndicatorCode());
        assertEquals("7.2", result.get(0).getValue()); // Tarkistetaan String-tyyppinen arvo
        verify(whoClient, times(1)).getIndicatorData(eq(indicatorCode), anyString());
    }

    @Test
    void testGetFilteredIndicatorData_InvalidFilter() {
        // Arrange
        String indicatorCode = "DIABETES";
        Map<String, String> filters = Map.of("InvalidKey", "InvalidValue");

        // Act & Assert
        WHOServiceException exception = assertThrows(WHOServiceException.class, () -> {
            whoService.getFilteredIndicatorData(indicatorCode, filters);
        });

        // Tarkista poikkeusviesti
        assertTrue(exception.getMessage().contains("Invalid filter key: InvalidKey"));
    }


    @Test
    void testGetFilteredIndicatorData_ClientThrowsException() {
        // Arrange
        String indicatorCode = "DIABETES";
        Map<String, String> filters = Map.of("SpatialDim", "FI");

        when(whoClient.getIndicatorData(eq(indicatorCode), anyString()))
            .thenThrow(new RuntimeException("WHO API error"));

        // Act & Assert
        WHOServiceException exception = assertThrows(WHOServiceException.class, () -> {
            whoService.getFilteredIndicatorData(indicatorCode, filters);
        });

        assertTrue(exception.getMessage().contains("Error fetching data from WHO"));
        verify(whoClient, times(1)).getIndicatorData(eq(indicatorCode), anyString());
    }

    @Test
    void testGetFilteredIndicatorData_NullResponse() {
        // Arrange
        String indicatorCode = "DIABETES";
        Map<String, String> filters = Map.of("SpatialDim", "FI");

        when(whoClient.getIndicatorData(eq(indicatorCode), anyString())).thenReturn(null);

        // Act & Assert
        WHOServiceException exception = assertThrows(WHOServiceException.class, () -> {
            whoService.getFilteredIndicatorData(indicatorCode, filters);
        });

        assertTrue(exception.getMessage().contains("Received null response from WHO"));
        verify(whoClient, times(1)).getIndicatorData(eq(indicatorCode), anyString());
    }
}
