package com.turtledoctor.kgu.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CookieNotFoundException extends RuntimeException {
    private final AuthErrorCode authErrorCode;
}
