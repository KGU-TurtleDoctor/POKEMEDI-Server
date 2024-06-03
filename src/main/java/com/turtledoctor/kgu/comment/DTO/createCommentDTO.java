package com.turtledoctor.kgu.comment.DTO;


import lombok.Data;

@Data
public class createCommentDTO {
    Long commentId;
    Long kakaoId;
    String nickName;
    String Body;
}
