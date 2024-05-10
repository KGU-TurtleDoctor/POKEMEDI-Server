package com.turtledoctor.kgu.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class PostForbiddenException extends RuntimeException{
    private ErrorCode errorCode;
}
