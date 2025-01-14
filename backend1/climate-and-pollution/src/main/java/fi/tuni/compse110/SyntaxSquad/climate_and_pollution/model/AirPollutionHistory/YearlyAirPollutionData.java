package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory;

import java.util.Map;

public class YearlyAirPollutionData {
    private String cityName;
    private double lat;
    private double lon;
    private int year;
    private Map<String, Double> averagedComponents; // Component names and their averages

    // Getters and setters
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<String, Double> getAveragedComponents() {
        return averagedComponents;
    }

    public void setAveragedComponents(Map<String, Double> averagedComponents) {
        this.averagedComponents = averagedComponents;
    }
}

