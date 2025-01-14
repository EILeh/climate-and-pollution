package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory;

public class DataPoint {

    private Main main;
    private Components components;
    private long dt; // Unix timestamp

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

    // Nested class for 'main'
    public static class Main {
        private int aqi;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }
    }
}
