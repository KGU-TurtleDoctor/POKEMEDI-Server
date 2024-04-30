package com.turtledoctor.kgu.comment.DTO.Request;

import lombok.Data;

@Data
public class UpdateCommentRequest {
    private String authorization;
    private Long postId;
    private Long commentId;
    private String Body;
}
