package it.unimol.vino.exceptions;

public class QuantityNotAvailableException extends RuntimeException{
    public QuantityNotAvailableException(String message) {
        super(message);
    }
}
