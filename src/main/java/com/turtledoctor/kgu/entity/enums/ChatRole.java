package com.turtledoctor.kgu.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
@Getter
public enum ChatRole {
    CHATBOT(0), USER(1);

    private int code;
}
