package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOIndicatorData;
/**
 * This class offers data for frontend!
 *   
 * @author Mikaela Ollila
 * @author Anssi ketomäki
 * 
 */
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.WHOService;

@RestController
@RequestMapping("/api")

public class HealthDataController {
    
    @Autowired
    private WHOService whoService;

    // Helper method to create filters and fetch data
    private List<WHOIndicatorData> getIndicatorData(String indicator, String country, String gtyear, String ltyear, Map<String, String> additionalFilters) {
        Map<String, String> filters = new HashMap<>(additionalFilters);
        filters.put("SpatialDim", country);
        filters.put("TimeDimGT", (gtyear != null) ? gtyear : "1000");
        filters.put("TimeDimLT", (ltyear != null) ? ltyear : "2100");

        List<WHOIndicatorData> data = whoService.getFilteredIndicatorData(indicator, filters);


        if (data != null && data.size() > 1) {
            data.sort(Comparator.comparingInt(WHOIndicatorData::getTimeDim));
        }

        return data;

    }

    // AIR_15: Household air pollution attributable DALYs
    @GetMapping("/air15")
    public List<WHOIndicatorData> getair15Data(
        @RequestParam String country, 
        @RequestParam (required = false) String gtyear, 
        @RequestParam (required = false) String ltyear){

        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSE_GHE000000");

        System.out.println("Fetching air15 data with parameters:"+ country + gtyear + ltyear);

        return getIndicatorData("AIR_15", country, gtyear, ltyear, filters);
    }

    // AIR_43: Ambient air pollution attributable DALYs
    @GetMapping("/air43")
    public List<WHOIndicatorData> getair43Data(
        @RequestParam String country, 
        @RequestParam (required = false) String gtyear, 
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching air43 data with parameters:"+ country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSES_GHE000");
        
        return getIndicatorData("AIR_43", country, gtyear, ltyear, filters);
    }

    // AIR_41: Ambient air pollution attributable deaths
    @GetMapping("/air41")
    public List<WHOIndicatorData> getair41Data(
        @RequestParam String country, 
        @RequestParam (required = false) String gtyear, 
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching air41 data with parameters:"+ country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSE_GHE000000");
        
        return getIndicatorData("AIR_41", country, gtyear, ltyear, filters);

    }
    // CCO_2: human development index rank
    @GetMapping("/cco2")
    public List<WHOIndicatorData> getCCO2Data(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching cco2 data with parameter: " + country + gtyear + ltyear);

        return getIndicatorData("CCO_2", country, gtyear, ltyear, new HashMap<>());

    }

    // AIR_35: Joint effects of air pollution attributable deaths
    @GetMapping("/air35")
    public List<WHOIndicatorData> getair35Data(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching air35 data with parameter: " + country + gtyear + ltyear);
        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSE_GHE000000");
        
        return getIndicatorData("AIR_35", country, gtyear, ltyear, filters);
    }

    // AIR_11: Household air pollution attributable deaths
    @GetMapping("/air11")
    public List<WHOIndicatorData> getair11Data(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching air11 data with parameter: " + country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSE_GHE000000");
        
        return getIndicatorData("AIR_11", country, gtyear, ltyear, filters);

    }

    // SDGPM25: Concentrations of fine particulate matter (PM2.5)
    @GetMapping("/sdgpm25")
    public List<WHOIndicatorData> getsdgpmData(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching sdgpm25 data with parameter: " + country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "RESIDENCEAREATYPE_TOTL");
        
        return getIndicatorData("SDGPM25", country, gtyear, ltyear, filters);
    }
    
    // SDGAIRBODA: Ambient and household air pollution attributable death rate (per 100 000
    // population, age-standardized)
    @GetMapping("/sdgairboda")
    public List<WHOIndicatorData> getsdgairbodaData(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching sdgairboda data with parameter: " + country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "SEX_BTSX", "Dim2", "GHECAUSE_GHE000000");
        
        return getIndicatorData("SDGAIRBODA", country, gtyear, ltyear, filters);
    }

    // PHE_HHAIR_POP_POLLUTING_FUELS: Population with primary reliance on polluting 
    // fuels and rechnologies for cooking (in millions)
    @GetMapping("/poppollutingfuels")
    public List<WHOIndicatorData> getpoppollutingfuelsData(
        @RequestParam String country,
        @RequestParam (required = false) String gtyear,
        @RequestParam (required = false) String ltyear) {

        System.out.println("Fetching poppollutingfuels data with parameter: " + country + gtyear + ltyear);

        Map<String, String> filters = Map.of("Dim1", "RESIDENCEAREATYPE_TOTL");
        return getIndicatorData("PHE_HHAIR_POP_POLLUTING_FUELS", country, gtyear, ltyear, filters);
    }

}
