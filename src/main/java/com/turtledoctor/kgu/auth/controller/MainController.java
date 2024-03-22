package com.turtledoctor.kgu.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/test/test")
    @ResponseBody
    public Map<String,Object> mainAPI(@CookieValue(name = "Authorization") String author) {
        Map<String,Object> result = new HashMap<>();
        result.put("test",author);

        return result;
    }
}
