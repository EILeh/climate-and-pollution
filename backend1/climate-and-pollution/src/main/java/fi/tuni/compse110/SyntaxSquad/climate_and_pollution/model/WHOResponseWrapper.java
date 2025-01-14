package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model;

/*
 *This class handles the WHO data stucture that is JSON,
 * but the actual data LIST is inside the VALUE of it. 
 */

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WHOResponseWrapper {
    
    @JsonProperty("value")
    private List<WHOIndicatorData> value;

    public List<WHOIndicatorData> getValue() {
        return value;
    }

    public void setValue(List<WHOIndicatorData> value) {
        this.value = value;
    }
}
