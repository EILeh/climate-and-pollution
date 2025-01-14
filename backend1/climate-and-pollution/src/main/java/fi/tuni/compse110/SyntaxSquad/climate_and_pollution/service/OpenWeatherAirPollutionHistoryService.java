package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherAirPollutionHistoryClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OpenWeatherLocationClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.*;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.AirPollutionHistoryData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.Components;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.DataPoint;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory.YearlyAirPollutionData;

@Service
public class OpenWeatherAirPollutionHistoryService {
    
    @Autowired
    private OpenWeatherAirPollutionHistoryClient historyClient;

    @Autowired
    private OpenWeatherLocationClient openWeatherLocationClient;

    @Value("${openweather.api.key}")
    private String apiKey;

    public List<YearlyAirPollutionData> getAirPollutionHistoryData(String cityName) {
        // Step 1: Fetch coordinates for the city using the location API
        List<LocationData> locations = openWeatherLocationClient.getLocationData(cityName, 5, apiKey);

        if (locations.isEmpty()) {
            throw new RuntimeException("City not found: " + cityName);
        }

        LocationData location = locations.get(0); // Get the first result

        // Step 2: Calculate Unix timestamps for the time range
        int currentUnixTime = (int) Instant.now().getEpochSecond(); // Now
        int startUnixTime = 1606435200; // Example: fixed start timestamp

        // Step 3: Fetch air pollution history data
        AirPollutionHistoryData pollutionHistoryData = historyClient.getAirPollutionHistoryData(
            String.valueOf(location.getLat()),
            String.valueOf(location.getLon()),
            String.valueOf(startUnixTime),
            String.valueOf(currentUnixTime),
            apiKey
        );

        // Step 4: Group data points by year and calculate yearly averages
        List<DataPoint> dataPoints = pollutionHistoryData.getList();
        Map<Integer, List<DataPoint>> groupedByYear = groupDataPointsByYear(dataPoints);

        // Step 5: Calculate yearly averages
        List<YearlyAirPollutionData> yearlyAverages = new ArrayList<>();
        for (Map.Entry<Integer, List<DataPoint>> entry : groupedByYear.entrySet()) {
            int year = entry.getKey();
            List<DataPoint> yearData = entry.getValue();

            Map<String, Double> averages = calculateAverages(yearData);

            YearlyAirPollutionData yearlyData = new YearlyAirPollutionData();
            yearlyData.setCityName(cityName);
            yearlyData.setLat(location.getLat());
            yearlyData.setLon(location.getLon());
            yearlyData.setYear(year);
            yearlyData.setAveragedComponents(averages);

            yearlyAverages.add(yearlyData);
        }

        return yearlyAverages;
    }

    // Helper: Group data points by year
    private Map<Integer, List<DataPoint>> groupDataPointsByYear(List<DataPoint> dataPoints) {
        return dataPoints.stream().collect(Collectors.groupingBy(dataPoint -> {
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(dataPoint.getDt()),
                ZoneId.systemDefault()
            );
            return dateTime.getYear();
        }));
    }

    // Helper: Calculate averages of components for a list of data points
    private Map<String, Double> calculateAverages(List<DataPoint> dataPoints) {
        Map<String, Double> componentSums = new HashMap<>();
        Map<String, Integer> componentCounts = new HashMap<>();
    
        // Sum all valid (non-negative) components
        for (DataPoint point : dataPoints) {
            Components components = point.getComponents();
    
            addValidComponent(componentSums, componentCounts, "co", components.getCo());
            addValidComponent(componentSums, componentCounts, "no", components.getNo());
            addValidComponent(componentSums, componentCounts, "no2", components.getNo2());
            addValidComponent(componentSums, componentCounts, "o3", components.getO3());
            addValidComponent(componentSums, componentCounts, "so2", components.getSo2());
            addValidComponent(componentSums, componentCounts, "pm2_5", components.getPm2_5());
            addValidComponent(componentSums, componentCounts, "pm10", components.getPm10());
            addValidComponent(componentSums, componentCounts, "nh3", components.getNh3());
        }
    
        // Calculate averages for each component
        return componentSums.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> componentCounts.get(entry.getKey()) == 0 ? 0.0 : roundToThreeDecimalPlaces(entry.getValue() / componentCounts.get(entry.getKey()))
                ));
    }
    
    // Helper method to add valid (non-negative) values to sums and counts
    private void addValidComponent(Map<String, Double> sums, Map<String, Integer> counts, String key, double value) {
        if (value >= 0) { // Only include non-negative values
            sums.merge(key, value, Double::sum);
            counts.merge(key, 1, Integer::sum);
        }
    }
    
    // Helper method to round a double to 3 decimal places
    private double roundToThreeDecimalPlaces(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
    
}
