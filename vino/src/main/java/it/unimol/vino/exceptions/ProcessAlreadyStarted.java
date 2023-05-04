package it.unimol.vino.exceptions;

public class ProcessAlreadyStarted extends RuntimeException {
    public ProcessAlreadyStarted(String message) {
        super(message);
    }
}
