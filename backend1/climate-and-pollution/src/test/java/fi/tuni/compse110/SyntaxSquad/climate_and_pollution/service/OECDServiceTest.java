
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDDroughtClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDExtremeTempClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDMortalityClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.OECDData;

import java.util.List;
/**
 * /**
 * This test class verifies the behavior of the OECDService,
 * which is responsible for fetching and processing environmental data from OECD-related clients.
 *
 * Key Points:
 * - The `@Mock` annotation is used to mock external clients such as `OECDMortalityClient`,
 *   `OECDExtremeTempClient`, and `OECDDroughtClient`, which simulate interactions with the OECD APIs.
 * - The `@InjectMocks` annotation is used to inject these mocks into the `OECDService` being tested.
 * - The `setUp` method initializes the mocks using `MockitoAnnotations.openMocks`.
 *
 * Tests:
 * 1. `testGetMortalityData`:
 *    - Verifies that the service correctly parses mortality data from a mocked XML response.
 *    - Ensures the parsed data matches the expected values, including year and value.
 * 2. `testGetXTempData`:
 *    - Verifies that the service correctly parses extreme temperature data from a mocked XML response.
 *    - Ensures the parsed data matches the expected values.
 * 3. `testGetDroughtData`:
 *    - Verifies the service's ability to parse and process drought data, including calculating cumulative indices.
 *    - Validates the calculation of cumulative indices based on a percentage increase over multiple years.
 * 4. `testDefineBuilder`:
 *    - Ensures that the `defineBuilder` method is properly implemented and returns a non-null value.
 * 
 * This test is done with the help of ChatGPT.
 * 
 */

class OECDServiceTest {

    @Mock
    private OECDMortalityClient mortalityClient;

    @Mock
    private OECDExtremeTempClient xTempClient;

    @Mock
    private OECDDroughtClient droughtClient;

    @InjectMocks
    private OECDService oecdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMortalityData() throws Exception {
        String mockResponse = """
            <root>
                <Obs>
                    <ObsDimension value="2001"/>
                    <ObsValue value="100.5"/>
                </Obs>
                <Obs>
                    <ObsDimension value="2002"/>
                    <ObsValue value="120.3"/>
                </Obs>
            </root>
        """;

        when(mortalityClient.getMortalityData(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(mockResponse);

        List<OECDData> result = oecdService.getMortalityData("FIN");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2001, result.get(0).getYear());
        assertEquals(100.5, result.get(0).getValue());
    }

    @Test
    void testGetXTempData() throws Exception {
        String mockResponse = """
            <root>
                <Obs>
                    <ObsDimension value="2005"/>
                    <ObsValue value="15.2"/>
                </Obs>
                <Obs>
                    <ObsDimension value="2006"/>
                    <ObsValue value="16.8"/>
                </Obs>
            </root>
        """;

        when(xTempClient.getExtremeTempData(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(mockResponse);

        List<OECDData> result = oecdService.getXTempData("USA");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2005, result.get(0).getYear());
        assertEquals(15.2, result.get(0).getValue());
    }

    @Test
    void testGetDroughtData() throws Exception {
        String mockResponse = """
            <root>
                <Obs>
                    <ObsDimension value="2010"/>
                    <ObsValue value="10.0"/> <!-- 10% increase -->
                </Obs>
                <Obs>
                    <ObsDimension value="2011"/>
                    <ObsValue value="10.0"/> <!-- 10% increase -->
                </Obs>
            </root>
        """;

        // Mock the response for the drought client
        when(droughtClient.getDroughtData(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(mockResponse);

        // Call the method to be tested
        List<OECDData> result = oecdService.getDroughtData("SWE");

        // Ensure the result is not null and contains the correct number of entries
        assertNotNull(result);
        assertEquals(2, result.size());

        // Calculate the expected index for the first year (2010)
        // Base value is 100, increased by 10%
        double firstYearIndex = 100 + (100 * 10 / 100); // First year's cumulative value
        assertEquals(2010, result.get(0).getYear());
        assertEquals(firstYearIndex, result.get(0).getValue(), 0.01);

        // Calculate the expected index for the second year (2011)
        // Based on the cumulative value of the first year
        double secondYearIndex = firstYearIndex + (firstYearIndex * 10 / 100); // Second year's cumulative value
        assertEquals(2011, result.get(1).getYear());
        assertEquals(secondYearIndex, result.get(1).getValue(), 0.01);
    }
    @Test
    void testDefineBuilder() throws Exception {
        assertNotNull(oecdService.defineBuilder());
    }
}
