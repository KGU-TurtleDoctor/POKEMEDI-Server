package com.turtledoctor.kgu.comment.DTO.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DeleteCommentRequest {
    private Long commentId;
    private Long postId;
}
