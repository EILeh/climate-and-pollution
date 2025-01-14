package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model;

import java.util.HashMap;
import java.util.Map;

public class LocationData {

    private String name;
    private Map<String, String> localNames;  // Dynamic local names
    private double lat;
    private double lon;
    private String country;

    // Constructor
    public LocationData(String name, Map<String, String> localNames, double lat, double lon, String country) {
        this.name = name;
        this.localNames = (localNames != null) ? localNames : new HashMap<>();
        this.lat = lat;
        this.lon = lon;
        this.country = country;
    }

    // Default constructor for deserialization
    public LocationData() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLocalNames() {
        return localNames;
    }

    public void setLocalNames(Map<String, String> localNames) {
        this.localNames = localNames;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "name='" + name + '\'' +
                ", localNames=" + localNames +
                ", lat=" + lat +
                ", lon=" + lon +
                ", country='" + country + '\'' +
                '}';
    }
    
}
