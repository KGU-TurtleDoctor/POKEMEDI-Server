package com.turtledoctor.kgu.auth.service;


import com.turtledoctor.kgu.auth.dto.UserDTO;
import com.turtledoctor.kgu.auth.jwt.JWTFilter;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MainService {


     @Value("${spring.jwt.secret}")
    String secret;
        JWTUtil jwtUtil;




    public MainService(){

    }


    public UserDTO returnInfo(String jwtToken){
        jwtUtil = new JWTUtil(secret);
        UserDTO userDTO = new UserDTO();
        userDTO.setName(jwtUtil.getName(jwtToken));
        userDTO.setKakaoId(jwtUtil.getkakaoId(jwtToken));
        userDTO.setEmail("");
        userDTO.setRole(jwtUtil.getRole(jwtToken));

        log.info("user 정보 저장 완 \n"+"userName:"+userDTO.getName()
                +"\nuserKaKaoId:"+userDTO.getKakaoId()
                +"");


        return userDTO;
    }


}
