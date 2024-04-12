package com.turtledoctor.kgu.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostListDTO {
    Long id;
    String title;
    String content;
    String nickname;
    String date;
}
