package com.turtledoctor.kgu.post.exception;

import com.turtledoctor.kgu.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class PostExceptionHandler {


    @ExceptionHandler(PostForbiddenException.class)
    public ResponseEntity<ResponseDTO> handlePostForbiddenException(PostForbiddenException ex){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(ex.getErrorCode().isSuccess())
                .stateCode(ex.getErrorCode().getStatus())
                .result(ex.getErrorCode().getMessage())
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }
}
