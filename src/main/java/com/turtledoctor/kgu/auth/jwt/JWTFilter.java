package com.turtledoctor.kgu.auth.jwt;

import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.exception.AuthErrorCode;
import com.turtledoctor.kgu.auth.exception.CookieNotFoundException;
import com.turtledoctor.kgu.auth.exception.JWTIsExipiredException;
import com.turtledoctor.kgu.auth.exception.JWTIsNotFoundException;
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

import static com.turtledoctor.kgu.auth.exception.AuthErrorCode.*; //errorcode 처리

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        // 쿠키가 없어 비어있을 시 Null 체크 추가 및 예외 처리
        if (cookies == null)
            throw new CookieNotFoundException(COOKIE_IS_NOT_EXIST);


        for (Cookie cookie : cookies) {

            System.out.println(cookie.getName());
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }
        //Authorization 헤더 검증
        //쿠키는 존재했는데, authorization을 key로 가진 쿠키가 없는 경우 체크임.
        if (authorization == null) {
            throw new JWTIsNotFoundException(COOKIE_EXIST_BUT_JWT_NOT_EXIST);
//            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
//            return;
        }

        //토큰
        String token = authorization;

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            throw new JWTIsExipiredException(COOKIE_IS_EXPIRED);
//            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
//            return;
        }

        //토큰에서 kakaoId과 role 획득
        String kakaoId = jwtUtil.getkakaoId(token);
        String name = jwtUtil.getName(token); // 추가
        String email = jwtUtil.getEmail(token); // 추가
        log.info(email+"\n12341234");
        String role = jwtUtil.getRole(token);

        //userDTO를 생성하여 값 set
        UserDTO userDTO = new UserDTO();
        userDTO.setKakaoId(kakaoId);
        userDTO.setName(name); // 추가
        userDTO.setEmail(email); // 추가
        userDTO.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
