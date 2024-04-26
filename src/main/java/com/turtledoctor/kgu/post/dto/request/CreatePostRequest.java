package com.turtledoctor.kgu.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {
    String title;
    String body;
    Long kakaoId;
}
