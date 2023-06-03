package it.unimol.vino.exceptions;

public class InvalidItemQuantityException extends RuntimeException{
    public InvalidItemQuantityException(String message) {
        super(message);
    }
}
