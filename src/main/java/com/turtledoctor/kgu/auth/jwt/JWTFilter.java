package com.turtledoctor.kgu.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.exception.AuthException;
import com.turtledoctor.kgu.exception.ErrorCode;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;

import static com.turtledoctor.kgu.exception.ErrorCode.*;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = null;
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                throw new AuthException(COOKIE_IS_NOT_EXIST);
            }
            for (Cookie cookie : cookies) {

                //            System.out.println(cookie.getName());
                if (cookie.getName().equals("Authorization")) {

                    authorization = cookie.getValue();
                }
            }
            //Authorization 헤더 검증
            if (authorization == null) {
                throw new AuthException(COOKIE_EXIST_BUT_JWT_NOT_EXIST);
            }

            //토큰
            String token = authorization;

            // 토큰 유효성 검증
            jwtUtil.validateToken(token);

            //토큰 소멸 시간 검증
            if (jwtUtil.isExpired(token)) {
                throw new AuthException(COOKIE_IS_EXPIRED);
            }

            //토큰에서 kakaoId과 role 획득
            String kakaoId = jwtUtil.getkakaoId(token);
            //        String name = jwtUtil.getName(token); // 제거
            //        String email = jwtUtil.getEmail(token); // 제거
            log.info("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")) + "]JWTFilter 거친 카카오 id : " + kakaoId + "\n");
            String role = jwtUtil.getRole(token);

            //userDTO를 생성하여 값 set
            UserDTO userDTO = new UserDTO();
            userDTO.setKakaoId(kakaoId);
            //        userDTO.setName(name); // 제거
            //        userDTO.setEmail(email); // 제거
            userDTO.setRole(role);

            //UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (AuthException ex) {
            setResponse(response, ex.getErrorCode());
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatus());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .isSuccess(errorCode.isSuccess())
                .stateCode(errorCode.getStatus())
                .result(errorCode.getMessage())
                .build();
        String jsonResponse = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().write(jsonResponse);
    }
}
