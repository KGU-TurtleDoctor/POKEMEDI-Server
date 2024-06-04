package com.turtledoctor.kgu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(false, 400, "파라미터 값을 확인해주세요."),

    //401 UNAUTHORIZED 인증 정보 없음
    UNAUTHORIZED(false, 401, "로그인 후 이용 가능합니다."),

    //403 FORBIDDEN 접근 권한 거부
    POST_FORBIDDEN(false, 403, "게시글 작성자만 접근 가능합니다."),

    //404 NOT_FOUND 잘못된 리소스 접근
    POST_NOT_FOUND(false, 404, "존재하지 않는 게시글입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(false, 500, "서버 에러입니다.");

    private final boolean success;
    private final int status;
    private final String message;
}
