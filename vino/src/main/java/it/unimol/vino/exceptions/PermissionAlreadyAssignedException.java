package it.unimol.vino.exceptions;

public class PermissionAlreadyAssignedException extends RuntimeException {
    public PermissionAlreadyAssignedException(String message) {
        super(message);
    }
}
