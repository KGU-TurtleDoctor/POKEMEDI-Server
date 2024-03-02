package com.turtledoctor.kgu.auth.kakao.controller;


import com.turtledoctor.kgu.auth.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;
    
    @GetMapping("/testInfo")
    @ResponseBody
    public Map<String, Object> test(){
        HashMap<String, Object> test = new HashMap<>();

        test.put("kakaoApiKey",kakaoApiKey);
        test.put("kakaoRedirectUri",kakaoRedirectUri);

        return test;
    }

    @GetMapping("/testLogin")
    @ResponseBody
    public Map<String, Object> testLogin(@RequestParam("code") String code){
        HashMap<String, Object> result = new HashMap<>();

        return kakaoLoginService.getUserInfo(kakaoLoginService.getAccessToken(code));
    }
}
