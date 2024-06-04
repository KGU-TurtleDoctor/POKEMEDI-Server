package com.turtledoctor.kgu.auth.exception;

public class CookieNotFoundException extends RuntimeException {
    public CookieNotFoundException(String message) {
        super(message);
    }
}
