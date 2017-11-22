package de.irissmann.arachni.client;

public class ArachniClientException extends Exception {

    private static final long serialVersionUID = 2088423549757451992L;

    public ArachniClientException(String message) {
        super(message);
    }
    
    public ArachniClientException(String message, Exception exception) {
        super(message, exception);
    }
}
