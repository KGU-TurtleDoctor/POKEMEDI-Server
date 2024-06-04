package com.turtledoctor.kgu.auth.exception;

public class JWTIsNotFoundException extends RuntimeException {
    public JWTIsNotFoundException(String message) {
        super(message);
    }
}
