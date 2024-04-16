package com.turtledoctor.kgu.reply;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessage {
    private String error;
}
