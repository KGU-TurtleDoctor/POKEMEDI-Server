package com.turtledoctor.kgu.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DeleteReplyDTO {
    private Long postId;
    private Long commentId;
    private Long replyId;
    private String authorization;
}
