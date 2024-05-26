package com.turtledoctor.kgu.auth.service;


import com.turtledoctor.kgu.auth.dto.LoginDTO;
import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
        UserDTO userDTO = new UserDTO();
        userDTO.setKakaoId(jwtUtil.getkakaoId(jwtToken));
        userDTO.setRole(jwtUtil.getRole(jwtToken));

        Member member = memberRepository.findBykakaoId(userDTO.getKakaoId());

        LoginDTO loginDTO = LoginDTO.builder()
                .name(member.getName())
                .userRole(member.getRole())
                .build();

        return loginDTO;
    }


}
