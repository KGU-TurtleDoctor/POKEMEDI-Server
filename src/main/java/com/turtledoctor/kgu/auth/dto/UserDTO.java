package com.turtledoctor.kgu.auth.dto;

import lombok.Data;

@Data //Getter Setter
public class UserDTO {
    private String role;
    private String name;
    private String username;
}
