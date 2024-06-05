package com.turtledoctor.kgu.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JWTIsExipiredException extends RuntimeException {
    private final AuthErrorCode authErrorCode;
}
