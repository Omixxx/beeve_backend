package it.unimol.vino.exceptions;

public class WasteNotAllowedException extends  RuntimeException{
    public WasteNotAllowedException(String message) {
        super(message);
    }
}
