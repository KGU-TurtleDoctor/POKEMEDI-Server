package com.turtledoctor.kgu.auth.dto;

import lombok.*;

import java.util.Map;

//@Builder
//@NoArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;
    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public Long getProviderId() {
        return Long.parseLong(attribute.get("id").toString());
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account"); // 형변환 체크 문제.
        return kakaoAccount.get("email").toString();

    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties"); // 형변환 체크 문제.
        return properties.get("nickname").toString();
    }
}
