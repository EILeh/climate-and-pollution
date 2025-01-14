
package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestErrorController {

    @GetMapping("/trigger-error")
    public ResponseEntity<Void> triggerError(@RequestParam(required = false) String exceptionType) throws Exception {
        if ("WHOServiceException".equals(exceptionType)) {
            throw new fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.WHOServiceException("WHO service failed");
        } else if ("OECDServiceException".equals(exceptionType)) {
            throw new fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.OECDServiceException("OECD service failed");
        } else if ("OpenWeatherServiceException".equals(exceptionType)) {
            throw new fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.OpenWeatherServiceException("OpenWeather service failed");
        } else {
            throw new RuntimeException("Unexpected error occurred");
        }
    }
}
