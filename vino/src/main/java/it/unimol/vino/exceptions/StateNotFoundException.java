package it.unimol.vino.exceptions;

public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException(String message) {
        super(message);
    }
}
