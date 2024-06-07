package com.turtledoctor.kgu.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turtledoctor.kgu.auth.exception.AuthException;
import com.turtledoctor.kgu.exception.ErrorCode;
import com.turtledoctor.kgu.response.ResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.turtledoctor.kgu.exception.ErrorCode.*;

@AllArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // GenericFilterBean은 필수적으로 doFilter 메서드를 구현해야 한다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        //get jwt token
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new AuthException(COOKIE_IS_NOT_EXIST);
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization"))
                authorization = cookie.getValue();
        }

        //Authorization 헤더 검증
        if (authorization == null) {
            throw new AuthException(COOKIE_EXIST_BUT_JWT_NOT_EXIST);
        }

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(authorization)) {
            throw new AuthException(COOKIE_IS_EXPIRED);
        }

        //로그아웃 진행

        //Refresh 토큰 Cookie 값 0
//        Cookie cookie = new Cookie("Authorization", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
        response.addHeader("Set-Cookie", createCookie("Authorization", null).toString());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(HttpServletResponse.SC_OK)
                .result("로그아웃 성공")
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), responseDTO);
    }

    private ResponseCookie createCookie(String key, String value) {
        ResponseCookie responseCookie = ResponseCookie.from(key, value)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .build();

        return responseCookie;
    }
}

