package com.turtledoctor.kgu.auth.service;


import com.turtledoctor.kgu.auth.dto.LoginDTO;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.dto.isLoginCheckDTO;
import com.turtledoctor.kgu.auth.exception.AuthException;
import com.turtledoctor.kgu.auth.jwt.JWTFilter;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.enums.UserRole;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.turtledoctor.kgu.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {




     @Value("${spring.jwt.secret}")
    String secret;

     JWTUtil jwtUtil;
    private final MemberRepository memberRepository;



    public LoginDTO returnInfo(String jwtToken){
        jwtUtil = new JWTUtil(secret);
        if(jwtUtil == null) {
            throw new AuthException(UNAUTHORIZED);
        }
        UserDTO userDTO = new UserDTO();
//        userDTO.setName(jwtUtil.getName(jwtToken)); // 제거
        userDTO.setKakaoId(jwtUtil.getkakaoId(jwtToken));
//        userDTO.setEmail(jwtUtil.getEmail(jwtToken)); // 제거
        userDTO.setRole(jwtUtil.getRole(jwtToken));

        Member member = memberRepository.findBykakaoId(userDTO.getKakaoId());

        LoginDTO loginDTO = LoginDTO.builder()
                .name(member.getName())
                .userRole(member.getRole())
                .build();



        return loginDTO;
    }

    public isLoginCheckDTO returnIsLogin(String jwtToken) {
        jwtUtil = new JWTUtil(secret);
        isLoginCheckDTO isLoginCheckDTO = new isLoginCheckDTO();
        boolean value = true;
        if(jwtToken == null)
            value = false;
        else
            jwtUtil.validateToken(jwtToken); // 아래 작업을 통해 jwt 존재 시 유효 검증

        isLoginCheckDTO.setLoginStatus(value);

        return isLoginCheckDTO;
    }


}
