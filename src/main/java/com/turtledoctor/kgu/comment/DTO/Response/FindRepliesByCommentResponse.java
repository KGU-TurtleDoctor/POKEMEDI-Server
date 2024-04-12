package com.turtledoctor.kgu.comment.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindRepliesByCommentResponse {
    private Long replyId;
    private String body;
    private String time;

    private String nickName;

}
