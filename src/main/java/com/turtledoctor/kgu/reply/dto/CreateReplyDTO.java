package com.turtledoctor.kgu.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CreateReplyDTO {
    private Long postId;
    private Long commentId;
    private String body;
    private String authorization;
}
