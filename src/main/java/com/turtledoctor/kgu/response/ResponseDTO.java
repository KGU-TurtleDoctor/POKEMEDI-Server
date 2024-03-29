package com.turtledoctor.kgu.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ResponseDTO {

    boolean result;
    int stateCode;
    Map<String,Object> object;

}
