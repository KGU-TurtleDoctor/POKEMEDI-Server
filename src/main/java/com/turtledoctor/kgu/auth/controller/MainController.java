package com.turtledoctor.kgu.auth.controller;

import com.sun.tools.javac.Main;
import com.turtledoctor.kgu.auth.service.MainService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/api/info")
    public ResponseEntity<ResponseDTO> mainAPI(@CookieValue(name = "Authorization") String author) {
        ResponseDTO re = ResponseDTO.builder().isSuccess(true).build();


        return ResponseEntity.ok().body(ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(mainService.returnInfo(author))
                .build());
    }
}

