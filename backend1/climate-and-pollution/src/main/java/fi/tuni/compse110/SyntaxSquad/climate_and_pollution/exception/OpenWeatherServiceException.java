package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception;

public class OpenWeatherServiceException extends RuntimeException {
    public OpenWeatherServiceException(String message) {
        super(message);
    }
    public OpenWeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
