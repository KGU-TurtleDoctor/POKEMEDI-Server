package com.turtledoctor.kgu.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequestDTO {
    String title;
    String body;
    Long kakaoId;
}
