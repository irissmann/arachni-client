package de.irissmann.arachni.api;

public class ArachniApiException extends Exception {

    private static final long serialVersionUID = 2088423549757451992L;

    public ArachniApiException(String message) {
        super(message);
    }
    
    public ArachniApiException(String message, Exception exception) {
        super(message, exception);
    }
}
