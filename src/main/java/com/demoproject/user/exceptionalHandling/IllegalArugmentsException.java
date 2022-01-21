package com.demoproject.user.exceptionalHandling;

public class IllegalArugmentsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalArugmentsException(String message) {
        super(message);
    }
}
