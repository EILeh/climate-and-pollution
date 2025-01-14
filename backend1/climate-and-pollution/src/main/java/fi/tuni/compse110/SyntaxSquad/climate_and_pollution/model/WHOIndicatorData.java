package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WHOIndicatorData {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("IndicatorCode")
    private String indicatorCode;

    @JsonProperty("SpatialDimType")
    private String spatialDimType;

    @JsonProperty("SpatialDim")
    private String spatialDim;

    @JsonProperty("ParentLocationCode")
    private String parentLocationCode;

    @JsonProperty("TimeDimType")
    private String timeDimType;

    @JsonProperty("ParentLocation")
    private String parentLocation;

    @JsonProperty("TimeDim")
    private Integer timeDim;

    @JsonProperty("Dim1")
    private String dim1;

    @JsonProperty("Dim2")
    private String dim2;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("NumericValue")
    private Double numericValue;

    @JsonProperty("Low")
    private Double low;

    @JsonProperty("High")
    private Double high;
    // Getters and Setters for each field

    public String getIndicatorCode() { return indicatorCode; }
    public void setIndicatorCode(String indicatorCode) { this.indicatorCode = indicatorCode; }

    public String getSpatialDim() { return spatialDim; }
    public void setSpatialDim(String spatialDim) { this.spatialDim = spatialDim; }

    public Integer getTimeDim() { return timeDim; }
    public void setTimeDim(Integer timeDim) { this.timeDim = timeDim; }

    public String getDim1() { return dim1; }
    public void setDim1(String dim1) { this.dim1 = dim1; }

    public String getDim2() { return dim2; }
    public void setDim2(String dim2) { this.dim2 = dim2; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Double getNumericValue() { return numericValue; }
    public void setNumericValue(Double numericValue) { this.numericValue = numericValue; }

}
