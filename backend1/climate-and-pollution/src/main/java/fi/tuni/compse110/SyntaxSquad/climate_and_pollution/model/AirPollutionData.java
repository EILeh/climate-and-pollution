package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model;

import java.util.List;

public class AirPollutionData {

    private Coord coord;
    private List<PollutionInfo> list;
    private String city;
    private String country;
    public AirPollutionData components;
    
    // Getters and setters
    public Coord getCoord() {
        return coord;
    }
    
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    
    public List<PollutionInfo> getList() {
        return list;
    }
    
    public void setList(List<PollutionInfo> list) {
        this.list = list;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    // Nested classes for inner structure of the JSON

    public static class Coord {
        private double lon;
        private double lat;

        // Getters and setters
        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class PollutionInfo {
        private Main main;
        private Components components;
        private long dt;

        // Getters and setters
        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Components getComponents() {
            return components;
        }

        public void setComponents(Components components) {
            this.components = components;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }
    }

    public static class Main {
        private int aqi; // Air Quality Index

        // Getters and setters
        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }
    }

    public static class Components {
        private double co;     // Carbon monoxide
        private double no;     // Nitrogen monoxide
        private double no2;    // Nitrogen dioxide
        private double o3;     // Ozone
        private double so2;    // Sulfur dioxide
        private double pm2_5;  // Fine particulate matter (PM2.5)
        private double pm10;   // Particulate matter (PM10)
        private double nh3;    // Ammonia

        // Getters and setters
        public double getCo() {
            return co;
        }

        public void setCo(double co) {
            this.co = co;
        }

        public double getNo() {
            return no;
        }

        public void setNo(double no) {
            this.no = no;
        }

        public double getNo2() {
            return no2;
        }

        public void setNo2(double no2) {
            this.no2 = no2;
        }

        public double getO3() {
            return o3;
        }

        public void setO3(double o3) {
            this.o3 = o3;
        }

        public double getSo2() {
            return so2;
        }

        public void setSo2(double so2) {
            this.so2 = so2;
        }

        public double getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(double pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public double getNh3() {
            return nh3;
        }

        public void setNh3(double nh3) {
            this.nh3 = nh3;
        }
    }
}
