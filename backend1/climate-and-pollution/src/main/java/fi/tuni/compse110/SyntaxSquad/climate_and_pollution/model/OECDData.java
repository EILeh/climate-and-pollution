package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model;

public class OECDData {
    private String countryCode; // CCA3 country code
    private int year;           // Observation year
    private double value;       // Observation value (ObsValue)

    // Constructor
    public OECDData(String countryCode, int year, double value) {
        this.countryCode = countryCode;
        this.year = year;
        this.value = value;
    }

    // Getters and Setters
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
