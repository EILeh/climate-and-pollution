package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception;

public class OECDServiceException extends RuntimeException {
    public OECDServiceException(String message){
        super(message);
        
    }
    public OECDServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
