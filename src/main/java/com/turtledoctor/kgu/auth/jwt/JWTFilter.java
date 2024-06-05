package com.turtledoctor.kgu.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.exception.*;
import com.turtledoctor.kgu.response.ResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

import static com.turtledoctor.kgu.auth.exception.AuthErrorCode.*; //errorcode 처리

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = null;
            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                throw new CookieNotFoundException(COOKIE_IS_NOT_EXIST);
            }

            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    authorization = cookie.getValue();
                }
            }

            if (authorization == null) {
                throw new JWTIsNotFoundException(COOKIE_EXIST_BUT_JWT_NOT_EXIST);
            }

            String token = authorization;

            if (jwtUtil.isExpired(token)) {
                throw new JWTIsExipiredException(COOKIE_IS_EXPIRED);
            }

            String kakaoId = jwtUtil.getkakaoId(token);
            String name = jwtUtil.getName(token);
            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);

            UserDTO userDTO = new UserDTO();
            userDTO.setKakaoId(kakaoId);
            userDTO.setName(name);
            userDTO.setEmail(email);
            userDTO.setRole(role);

            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            if (ex instanceof CookieNotFoundException) {
                handleException(response, ((CookieNotFoundException) ex).getAuthErrorCode());
            } else if (ex instanceof JWTIsNotFoundException) {
                handleException(response, ((JWTIsNotFoundException) ex).getAuthErrorCode());
            } else if (ex instanceof JWTIsExipiredException) {
                handleException(response, ((JWTIsExipiredException) ex).getAuthErrorCode());
            } else {
                throw ex;  // 예상하지 못한 예외를 다시 던짐
            }
        }
    }

    private void handleException(HttpServletResponse response, AuthErrorCode errorCode) throws IOException {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(errorCode.isSuccess())
                .stateCode(errorCode.getStatus())
                .result(errorCode.getMessage())
                .build();

        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json; charset=UTF-8"); // 인코딩을 UTF-8로 설정
        response.setCharacterEncoding("UTF-8"); // 응답의 문자 인코딩을 UTF-8로 설정
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
    }
}
