package me.yushust.cherrychat.exceptions;

public class UnimplementedException extends RuntimeException {

    private static final long serialVersionUID = 5772841950650741994L;

    public UnimplementedException(String message) {
        super(message);
    }

    public UnimplementedException(Exception exception) {
        super(exception);
    }

}
