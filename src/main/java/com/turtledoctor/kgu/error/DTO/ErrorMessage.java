package com.turtledoctor.kgu.error.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {
    String errorMessage;
}
