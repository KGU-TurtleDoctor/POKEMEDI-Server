package com.turtledoctor.kgu.auth.dto;

import lombok.*;

import java.util.Map;

//@Builder
//@NoArgsConstructor
public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;
    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}

