package com.turtledoctor.kgu.comment.DTO.Request;

import com.turtledoctor.kgu.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequest {
    private String authorization;
    private Long postId;
    private String body;
}
