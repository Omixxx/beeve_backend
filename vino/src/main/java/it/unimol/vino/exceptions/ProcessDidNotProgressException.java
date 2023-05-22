package it.unimol.vino.exceptions;

public class ProcessDidNotProgressException extends  RuntimeException{
    public ProcessDidNotProgressException(String message) {
        super(message);
    }
}
