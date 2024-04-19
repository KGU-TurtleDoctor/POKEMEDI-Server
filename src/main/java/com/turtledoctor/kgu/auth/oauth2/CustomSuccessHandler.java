package com.turtledoctor.kgu.auth.oauth2;

import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Value("${redirectURL}")
    String url;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        Long kakaoId = customUserDetails.getKakaoId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", kakaoId, role, 60*60*10L);
        String refresh = jwtUtil.createJwt("refresh", kakaoId, role, 24*60*60*10L);


        /*
        httpServletResponse의 addHeader : 동일한 이름의 헤더를 여러 번 추가할 수 있음.
        httpServletResponse의 setHeader : 마지막 호출이 이전의 값을 덮어씀.
        계속해서 갱신해야하는 accessToken 특성상 setHeader 사용.
         */
        response.setHeader("access", access);
//        response.addCookie(createCookie("refresh", refresh)); //addCookie대신 addHeader 사용하였음.
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("Set-Cookie",createCookie("refresh", refresh).toString());
//        response.addHeader("QWWWW",token);
        response.sendRedirect(url);
    }

//    private Cookie createCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }

    private ResponseCookie createCookie(String key, String value) {
        ResponseCookie responseCookie = ResponseCookie.from(key,value)
                .path("/")
                .sameSite("None")
                .maxAge(24*60*60)
                .secure(true)
                .httpOnly(true)
                .build();

        return responseCookie;
    }

}
