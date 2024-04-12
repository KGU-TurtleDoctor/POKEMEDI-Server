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
    private List<FindRepliesByCommentResponse> replies;
}
