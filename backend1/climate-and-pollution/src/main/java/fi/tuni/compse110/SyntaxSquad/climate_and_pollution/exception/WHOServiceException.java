package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception;

// Created with help of ChatGPT
public class WHOServiceException extends RuntimeException {

    /**
     * Luo uuden poikkeuksen ilman viestiä tai aiheuttajaa.
     */
    public WHOServiceException() {
        super();
    }

    /**
     * Luo uuden poikkeuksen annetulla viestillä.
     *
     * @param message Virheviesti
     */
    public WHOServiceException(String message) {
        super(message);
    }

    /**
     * Luo uuden poikkeuksen annetulla viestillä ja aiheuttajalla.
     *
     * @param message Virheviesti
     * @param cause   Poikkeuksen aiheuttaja
     */
    public WHOServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Luo uuden poikkeuksen annetulla aiheuttajalla.
     *
     * @param cause Poikkeuksen aiheuttaja
     */
    public WHOServiceException(Throwable cause) {
        super(cause);
    }
}
