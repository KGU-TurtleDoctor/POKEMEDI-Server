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
        Map<String,Object> result = new HashMap<>();
        result.put("userInfo",mainService.returnInfo(author));
        return ResponseEntity.ok().body(ResponseDTO.builder().result(true).stateCode(200).object(result).build());
    }
}

