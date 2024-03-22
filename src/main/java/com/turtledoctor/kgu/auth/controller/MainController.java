package com.turtledoctor.kgu.auth.controller;

import com.sun.tools.javac.Main;
import com.turtledoctor.kgu.auth.service.MainService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/test/test")
    @ResponseBody
    public Map<String,Object> mainAPI(@CookieValue(name = "Authorization") String author) {
        Map<String,Object> result = new HashMap<>();
        result.put("test",mainService.returnInfo(author));

        return result;
    }
}
