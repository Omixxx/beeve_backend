package it.unimol.vino.exceptions;

public class PermissionNotFound extends RuntimeException {
    public PermissionNotFound(String message) {
        super(message);
    }
}
