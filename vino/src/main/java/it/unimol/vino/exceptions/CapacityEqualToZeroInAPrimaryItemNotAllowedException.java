package it.unimol.vino.exceptions;

public class CapacityEqualToZeroInAPrimaryItemNotAllowedException extends RuntimeException {
    public CapacityEqualToZeroInAPrimaryItemNotAllowedException(String message) {
        super(message);
    }
}
