package com.turtledoctor.kgu.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@JsonPropertyOrder({"success", "stateCode", "result"})
public class ResponseDTO {

    boolean isSuccess;
    int stateCode;
    Object result;

}
