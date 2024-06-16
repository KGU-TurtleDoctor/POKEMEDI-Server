package com.turtledoctor.kgu.auth.dto;


import com.turtledoctor.kgu.entity.enums.UserRole;
import lombok.*;

@Getter
@Builder
public class LoginDTO {
    private String name;
    private UserRole userRole;
}
