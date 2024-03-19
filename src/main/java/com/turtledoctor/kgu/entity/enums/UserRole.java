package com.turtledoctor.kgu.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    NORMAL("normal"), DOCTOR("doctor"), ADMIN("admin");
    private final String userRole;
}
