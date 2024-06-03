package com.turtledoctor.kgu.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDetailResponse {
    String title;
    String content;
    String nickname;
    String date;
    Long likes;
    Long comments;
    boolean isWriter;
    boolean isLiked;
}
