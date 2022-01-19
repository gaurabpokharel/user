package com.demoproject.user.exceptionalHandling;


public class BadCredentialException1 extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadCredentialException1(String message) {
        super(message);
    }
}
