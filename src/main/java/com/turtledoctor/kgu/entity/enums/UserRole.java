package com.turtledoctor.kgu.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    NORMAL("normal"), MEMBERSHIP("membership"), DOCTOR("doctor");
    private final String userRole;
}
