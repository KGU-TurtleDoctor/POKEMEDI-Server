package com.turtledoctor.kgu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**400 BAD_REQUEST 잘못된 요청*/

    //쿠키 없을 경우
    COOKIE_IS_NOT_EXIST(false, 400, "쿠키가 존재하지 않습니다."),


    /**401 UNAUTHORIZED 인증 정보 없음*/

    //로그인 인증을 실패한 경우
    UNAUTHORIZED(false, 401, "로그인 후 이용 가능합니다."),

    //쿠키는 존재하나 authorization를 key로 하는 쿠키가 없을 경우(jwt 없음)
    COOKIE_EXIST_BUT_JWT_NOT_EXIST(false, 401, "인가 처리를 위한 jwt 토큰이 쿠키에 존재하지 않습니다."),


    /**403 FORBIDDEN 접근 권한 거부*/

    //게시글 작성자가 아닌데 권한을 요청하는 경우
    POST_FORBIDDEN(false, 403, "게시글 작성자만 접근 가능합니다."),

    //쿠키 만료, 사용할 수 없음.
    COOKIE_IS_EXPIRED(false, 403, "쿠키가 만료되어 사용할 수 없습니다."),

    //jwt 시그니처 에러, 토큰이 유효하지 않음.
    INVALID_JWT_SIGNATURE(false, 403, "jwt 유효성 검사를 통과하지 못했습니다."),


    /**404 NOT_FOUND 잘못된 리소스 접근*/

    //존재하지 않는 게시글 id를 요청하는 경우
    POST_NOT_FOUND(false, 404, "존재하지 않는 게시글입니다."),


    /**500 INTERNAL SERVER ERROR*/
    INTERNAL_SERVER_ERROR(false, 500, "서버 에러입니다.");






    private final boolean success;
    private final int status;
    private final String message;
}
