//package com.turtledoctor.kgu.auth.controller;
//
//import com.turtledoctor.kgu.auth.jwt.JWTUtil;
//import com.turtledoctor.kgu.response.ResponseDTO;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@AllArgsConstructor
//public class LoginCheckController {
//
//    private final JWTUtil jwtUtil;
//
//    @GetMapping("/api/isLogin")
//    public ResponseDTO checkIsLogin(HttpServletRequest request) {
//        boolean isLogin = false;
//        String authorization = null;
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("Authorization".equals(cookie.getName())) {
//                    authorization = cookie.getValue();
//                    break;
//                }
//            }
//
//            if (authorization != null && !jwtUtil.isExpired(authorization)) {
//                isLogin = true;
//            }
//        }
//
//        return ResponseDTO.builder()
//                .isSuccess(true)
//                .stateCode(200)
//                .result(isLogin)
//                .build();
//    }
//}
