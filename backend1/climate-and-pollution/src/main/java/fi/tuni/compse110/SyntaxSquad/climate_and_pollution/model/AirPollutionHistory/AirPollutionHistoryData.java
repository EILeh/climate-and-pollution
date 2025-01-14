package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.AirPollutionHistory;

import java.util.List;

public class AirPollutionHistoryData {

    private Coord coord;
    private List<DataPoint> list;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<DataPoint> getList() {
        return list;
    }

    public void setList(List<DataPoint> list) {
        this.list = list;
    }

    // Nested class for 'coord'
    public static class Coord {
        private double lon;
        private double lat;

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
}

