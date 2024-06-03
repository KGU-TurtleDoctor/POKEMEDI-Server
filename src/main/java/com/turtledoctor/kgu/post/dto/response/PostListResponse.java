package com.turtledoctor.kgu.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostListResponse {
    Long id;
    String title;
    String content;
    String nickname;
    String date;
    boolean isWriter;
}
