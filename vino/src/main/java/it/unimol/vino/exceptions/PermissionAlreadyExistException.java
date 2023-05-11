package it.unimol.vino.exceptions;

public class PermissionAlreadyExistException extends RuntimeException {
    public PermissionAlreadyExistException(String message) {
        super(message);
    }
}
