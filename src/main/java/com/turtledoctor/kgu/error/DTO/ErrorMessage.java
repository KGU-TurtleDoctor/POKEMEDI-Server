package com.turtledoctor.kgu.error.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class ErrorMessage {
    String errorMessage;
}
