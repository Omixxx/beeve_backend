package it.unimol.vino.exceptions;

public class ProcessAlreadyStartedException extends RuntimeException {
    public ProcessAlreadyStartedException(String message) {
        super(message);
    }
}
