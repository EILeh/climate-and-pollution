package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.WHOResponseWrapper;

// GlobalExceptionHandler handles errors
@FeignClient(name = "whoClient", url = "https://ghoapi.azureedge.net")
public interface WHOClient {

    @GetMapping("/api/{indicatorCode}")
    WHOResponseWrapper getIndicatorData(
        @PathVariable String indicatorCode,
        @RequestParam("$filter") String filterExpression
    );
}
