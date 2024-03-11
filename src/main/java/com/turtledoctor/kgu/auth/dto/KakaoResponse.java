package com.turtledoctor.kgu.auth.dto;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoResponse {
    //제공자에서 발급해주는 아이디(번호)
    private String ProviderId;
    //이메일
    private String Email;
    //닉네임
    private String Name;

}
