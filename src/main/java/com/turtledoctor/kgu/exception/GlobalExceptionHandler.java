package com.turtledoctor.kgu.exception;

import com.turtledoctor.kgu.auth.exception.AuthException;
import com.turtledoctor.kgu.post.exception.PostException;
import com.turtledoctor.kgu.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ResponseDTO> handlePostException(PostException ex){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(ex.getErrorCode().isSuccess())
                .stateCode(ex.getErrorCode().getStatus())
                .result(ex.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(responseDTO);
    }


    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseDTO> handleAuthException(AuthException ex){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(ex.getErrorCode().isSuccess())
                .stateCode(200)
                .result(ex.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(responseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDTO> handleAuthenticationException(AuthenticationException ex) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(false)
                .stateCode(ErrorCode.UNAUTHORIZED.getStatus())
                .result(ErrorCode.UNAUTHORIZED.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }
}
