package it.unimol.vino.exceptions;

public class PermissionAlreadyAssigned extends RuntimeException {
    public PermissionAlreadyAssigned(String message) {
        super(message);
    }
}
