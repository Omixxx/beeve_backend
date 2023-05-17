package it.unimol.vino.exceptions;

public class ProcessIsCompletedException extends RuntimeException {
    public ProcessIsCompletedException(String message) {
        super(message);
    }
}
