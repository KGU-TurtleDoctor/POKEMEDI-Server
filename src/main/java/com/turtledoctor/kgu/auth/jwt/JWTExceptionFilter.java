//package com.turtledoctor.kgu.auth.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.turtledoctor.kgu.auth.exception.AuthException;
//import com.turtledoctor.kgu.exception.ErrorCode;
//import com.turtledoctor.kgu.response.ResponseDTO;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//@Component
//public class JWTExceptionFilter extends OncePerRequestFilter {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(request, response);
//        } catch (AuthException ex) {
//            setResponse(response, ex.getErrorCode());
//        }
//    }
//    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws RuntimeException, IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(errorCode.getStatus());
//
//        ResponseDTO responseDTO = ResponseDTO.builder()
//                .isSuccess(errorCode.isSuccess())
//                .stateCode(errorCode.getStatus())
//                .result(errorCode.getMessage())
//                .build();
//        String jsonResponse = objectMapper.writeValueAsString(responseDTO);
//        response.getWriter().write(jsonResponse);
//    }
//}
