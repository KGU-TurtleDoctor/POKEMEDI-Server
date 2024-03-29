package com.turtledoctor.kgu.auth.dto;

import com.turtledoctor.kgu.entity.enums.UserRole;
import lombok.Data;

@Data //Getter Setter
public class UserDTO {
    private String role;
    private String name;
    private String kakaoId;
    private String email;
}
