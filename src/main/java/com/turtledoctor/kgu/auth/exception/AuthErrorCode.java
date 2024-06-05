package com.turtledoctor.kgu.auth.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode{

    //여기서부턴 커스텀 에러 처리.

    //쿠키 미존재시
    COOKIE_IS_NOT_EXIST(false, 2000, "쿠키가 존재하지 않습니다."),

    //쿠키는 존재하나 authorization를 key로 하는 쿠키가 미존재시(jwt 없음)
    COOKIE_EXIST_BUT_JWT_NOT_EXIST(false, 2001, "인가 처리를 위한 jwt 토큰이 쿠키에 존재하지 않습니다."),

    //쿠키 만료, 사용할 수 없음.
    COOKIE_IS_EXPIRED(false, 2002, "쿠키가 만료되어 사용할 수 없습니다."),

    //jwt 시그니처 에러, 토큰이 유효하지 않음.
    INVALID_JWT_SIGNITURE(false, 2003, "jwt 유효성 검사를 통과하지 못했습니다.");



    private final boolean success;
    private final int status;
    private final String message;
}
