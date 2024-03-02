package com.turtledoctor.kgu.auth.kakao.controller;


import com.turtledoctor.kgu.auth.kakao.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    //백엔드 프론트 통합 전 인가 코드 수동 입력
    private String code = "lMDV3psNWs41gBI4bv1RAAkhpy-j_1QeiiksaVgCnDDEe37RMMzKR3sOTyEKKcleAAABjf5TpMTmTYKY7N6ACw";

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
    public Map<String, Object> testLogin(){
        HashMap<String, Object> result = new HashMap<>();

        return kakaoLoginService.getUserInfo(kakaoLoginService.getAccessToken(code));
    }
}
