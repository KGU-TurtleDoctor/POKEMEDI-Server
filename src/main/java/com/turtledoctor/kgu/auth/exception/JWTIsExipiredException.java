package com.turtledoctor.kgu.auth.exception;

public class JWTIsExipiredException extends RuntimeException {
    public JWTIsExipiredException(String message) {
        super(message);
    }
}
