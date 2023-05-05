package it.unimol.vino.exceptions;

public class ProcessCancelledException extends RuntimeException {
    public ProcessCancelledException(String message) {
        super(message);
    }

}
