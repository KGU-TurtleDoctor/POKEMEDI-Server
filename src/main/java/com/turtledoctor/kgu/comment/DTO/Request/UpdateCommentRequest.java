package com.turtledoctor.kgu.comment.DTO.Request;

import lombok.Data;

@Data
public class UpdateCommentRequest {
    private Long commentId;
    private String Body;
}
