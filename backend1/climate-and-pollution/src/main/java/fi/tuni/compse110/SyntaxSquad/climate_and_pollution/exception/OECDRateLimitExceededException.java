package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception;

public class OECDRateLimitExceededException extends RuntimeException {
    public OECDRateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
