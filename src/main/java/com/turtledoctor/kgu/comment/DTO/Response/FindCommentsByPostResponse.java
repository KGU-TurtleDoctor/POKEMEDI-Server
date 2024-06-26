package com.turtledoctor.kgu.comment.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class FindCommentsByPostResponse {
    private Long commentId;
    private String nickName;
    private String body;
    private String time;
    private Boolean isWriter;
    private Boolean isPostWriter;
    private List<FindRepliesByCommentResponse> replies;
}
