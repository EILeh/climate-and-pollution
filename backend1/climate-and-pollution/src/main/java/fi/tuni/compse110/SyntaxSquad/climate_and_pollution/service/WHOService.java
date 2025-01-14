package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.WHOClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.WHOServiceException;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOIndicatorData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOResponseWrapper;

/**
 * This service handles communication with the WHO API,
 * retrieving diabetes registry data based on the country code.
 * It uses the WHOClient Feign client to interact with the API.
 * 
 */


// !! AI used as a helping tool to create exception handling!!
@Service
public class WHOService {

    @Autowired
    private WHOClient whoClient;

    public List<WHOIndicatorData> getFilteredIndicatorData(
        String indicatorCode,
        Map<String, String> filters) {

    // Lista sallituista avaimista
    List<String> allowedKeys = List.of("SpatialDim", "TimeDimGT", "TimeDimLT", "Dim1", "Dim2");

    // Tarkista, ettei filters sisällä tuntemattomia avaimia
    for (String key : filters.keySet()) {
        if (!allowedKeys.contains(key)) {
            throw new WHOServiceException("Invalid filter key: " + key);
        }
    }

    String filterExpression;

    try {
        // Rakennetaan suodatin ilmaisu
        filterExpression = filters.entrySet().stream()
            .map(entry -> {
                String filter;
                String operator;
                if (entry.getKey().equals("SpatialDim")) {
                    operator = "eq";
                    filter = "SpatialDim";
                } else if (entry.getKey().equals("TimeDimGT")) {
                    operator = "gt";
                    filter = "TimeDim";
                } else if (entry.getKey().equals("TimeDimLT")) {
                    operator = "lt";
                    filter = "TimeDim";
                } else if (entry.getKey().equals("Dim1")) {
                    operator = "eq";
                    filter = "Dim1";
                } else if (entry.getKey().equals("Dim2")) {
                    operator = "eq";
                    filter = "Dim2";
                } else {
                    operator = "eq";
                    filter = "";
                }

                return filter + " " + operator + " " +
                        (filter.equals("SpatialDim") || filter.equals("Dim1") || filter.equals("Dim2")
                                ? "'" + entry.getValue() + "'"
                                : entry.getValue());
            })
            .collect(Collectors.joining(" and "));
    } catch (Exception e) {
        throw new WHOServiceException("Error constructing filter expression", e);
    }

    System.out.println("haetaan who " + indicatorCode + ":sta filttereillä: " + filterExpression);
    WHOResponseWrapper response;

    try {
        response = whoClient.getIndicatorData(indicatorCode, filterExpression);
    } catch (Exception e) {
        throw new WHOServiceException("Error fetching data from WHO", e);
    }

    if (response == null || response.getValue() == null) {
        throw new WHOServiceException("Received null response from WHO");
    }

    // Palautetaan indikaattoridata listana
    return response.getValue();
}

}
