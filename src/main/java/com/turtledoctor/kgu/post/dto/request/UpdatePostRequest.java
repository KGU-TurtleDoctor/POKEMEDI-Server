package com.turtledoctor.kgu.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePostRequest {
    String title;
    String body;
    Long kakaoId;
    Long postId;
}