package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Created with help of ChatGPT
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WHOServiceException.class)
    public ResponseEntity<String> handleWHOServiceException(WHOServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("WHOService Error: " + e.getMessage());
    }
    
    @ExceptionHandler(OECDServiceException.class)
    public ResponseEntity<String> handleOECDServiceException(OECDServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("OECDService Error: " + e.getMessage());
    }
    @ExceptionHandler(OECDRateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimitExceeded(OECDRateLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Service unavailable: API rate limit exceeded (20 queries/hour). Please try again later.");
    }

    // Used ChatGPT code as a guideline to implement this exception handler
    @ExceptionHandler (OpenWeatherServiceException.class)
    public ResponseEntity<String> handleOpenWeatherServiceException(OpenWeatherServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("OpenWeatherAirPollutionService Error: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An unexpected error: " + e.getMessage());
    }
    
}
