package com.turtledoctor.kgu.comment.DTO.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCommentResponse {
    private Long commentId;
}
