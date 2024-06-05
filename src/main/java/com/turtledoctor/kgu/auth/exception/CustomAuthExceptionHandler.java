package com.turtledoctor.kgu.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turtledoctor.kgu.response.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication exception: ", authException);
        handleException(response, AuthErrorCode.INVALID_JWT_SIGNITURE);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access denied exception: ", accessDeniedException);
        handleException(response, AuthErrorCode.COOKIE_EXIST_BUT_JWT_NOT_EXIST);
    }

    @ExceptionHandler(CookieNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleCookieNotFoundException(CookieNotFoundException ex) {
        log.error("CookieNotFoundException: ", ex);
        return buildErrorResponse(ex.getAuthErrorCode());
    }

    @ExceptionHandler(JWTIsNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleJWTForAuthIsNotFoundException(JWTIsNotFoundException ex) {
        log.error("JWTIsNotFoundException: ", ex);
        return buildErrorResponse(ex.getAuthErrorCode());
    }

    @ExceptionHandler(JWTIsExipiredException.class)
    public ResponseEntity<ResponseDTO> handleJWTIsExiprationException(JWTIsExipiredException ex) {
        log.error("JWTIsExipiredException: ", ex);
        return buildErrorResponse(ex.getAuthErrorCode());
    }

    // 공통적인 예외 처리 메서드
    private void handleException(HttpServletResponse response, AuthErrorCode errorCode) throws IOException {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(errorCode.isSuccess())
                .stateCode(errorCode.getStatus())
                .result(errorCode.getMessage())
                .build();

        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
    }

    // ResponseDTO를 사용하여 ResponseEntity를 빌드하는 메서드
    private ResponseEntity<ResponseDTO> buildErrorResponse(AuthErrorCode errorCode) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(errorCode.isSuccess())
                .stateCode(errorCode.getStatus())
                .result(errorCode.getMessage())
                .build();

        log.info("Error response generated: {}", responseDTO);
        return ResponseEntity.status(errorCode.getStatus()).body(responseDTO);
    }
}
