package com.turtledoctor.kgu.post.exception;

import com.turtledoctor.kgu.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostException extends RuntimeException {
    private final ErrorCode errorCode;
}
