package com.turtledoctor.kgu.auth.dto;


import com.turtledoctor.kgu.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class LoginDTO {
    private String name;
    private UserRole userRole;
}
