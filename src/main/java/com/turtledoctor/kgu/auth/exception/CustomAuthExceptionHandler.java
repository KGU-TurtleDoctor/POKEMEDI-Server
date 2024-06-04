package com.turtledoctor.kgu.auth.exception;

import com.turtledoctor.kgu.response.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication error: " + authException.getMessage());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handleException(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: " + accessDeniedException.getMessage());
    }

    @ExceptionHandler(CookieNotFoundException.class)
    public void handleCookieNotFoundException(CookieNotFoundException ex, HttpServletResponse response) throws IOException {
        handleException(response, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(JWTIsNotFoundException.class)
    public void handleJWTForAuthIsNotFoundException(JWTIsNotFoundException ex, HttpServletResponse response) throws IOException {
        handleException(response, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(JWTIsExipiredException.class)
    public void handleJWTIsExiprationException(JWTIsExipiredException ex, HttpServletResponse response) throws IOException {
        handleException(response, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }

    // 공통적인 예외 처리 메서드
    private ResponseEntity<ResponseDTO> handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(false)
                .stateCode(statusCode)
                .result(message)
                .build();

        return ResponseEntity.status(response.getStatus()).body(responseDTO);
    }
}
