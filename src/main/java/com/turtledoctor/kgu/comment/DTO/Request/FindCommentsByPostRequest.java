package com.turtledoctor.kgu.comment.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCommentsByPostRequest {
    Long postId;
    String author;
}
