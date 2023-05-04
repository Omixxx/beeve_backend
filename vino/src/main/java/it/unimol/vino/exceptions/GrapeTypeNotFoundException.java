package it.unimol.vino.exceptions;

public class GrapeTypeNotFoundException extends RuntimeException{
    public GrapeTypeNotFoundException(String message) {
        super(message);
    }
}
