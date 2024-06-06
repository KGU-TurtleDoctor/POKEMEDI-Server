package com.turtledoctor.kgu.auth.controller;

import com.sun.tools.javac.Main;
import com.turtledoctor.kgu.auth.service.MainService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/api/info")
    public ResponseEntity<ResponseDTO> mainAPI(@CookieValue(name = "Authorization") String author) {
        ResponseDTO re = ResponseDTO.builder().isSuccess(true).build();

        ResponseEntity<ResponseDTO> response = ResponseEntity.ok().body(ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(mainService.returnInfo(author))
                .build());

            log.info(response.toString());

        return response;
    }

    @GetMapping("/api/isLogin")
    public ResponseEntity<ResponseDTO> isLoginCheckAPI(@CookieValue(name = "Authorization", required = false) String jwtToken) {

        ResponseEntity<ResponseDTO> response = ResponseEntity.ok().body(ResponseDTO.builder()
                        .isSuccess(true)
                        .stateCode(200)
                        .result(mainService.returnIsLogin(jwtToken))
                        .build());

        return response;
    }
}

