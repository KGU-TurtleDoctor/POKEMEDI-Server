package com.turtledoctor.kgu.auth.exception;

import com.turtledoctor.kgu.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException{
    private final ErrorCode errorCode;
}
