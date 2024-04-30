package com.turtledoctor.kgu.auth.oauth2;

import com.turtledoctor.kgu.auth.dto.CustomOAuth2User;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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
        String KakaoId = customUserDetails.getKakaoId();
        String email = customUserDetails.getEmail();
        String name = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(KakaoId, name, email, role, 60*60*60L);
        
        response.addHeader("Set-Cookie",createCookie("Authorization", token).toString());
        response.addHeader("QWWWW",token);
        response.sendRedirect(url);
    }

//    private Cookie createCookie(String key, String value) {
//        ResponseCookie responseCookie;
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true); //https 통신에서만 가능하게 cookie same-site를 none으로 하려면 해당 설정이 필요
//        cookie.setPath("/");
//        cookie.setHttpOnly(true); //js가 가져가지 못하게.
//
//        return cookie;
//    }

    private ResponseCookie createCookie(String key, String value) {
        ResponseCookie responseCookie = ResponseCookie.from(key,value)
                .path("/")
                .sameSite("None")
                .maxAge(60*60*60)
                .secure(true)
                .httpOnly(true)
                .build();

        return responseCookie;
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true); //https 통신에서만 가능하게 cookie same-site를 none으로 하려면 해당 설정이 필요
//        cookie.setPath("/");
//        cookie.setHttpOnly(true); //js가 가져가지 못하게.

    }
}
