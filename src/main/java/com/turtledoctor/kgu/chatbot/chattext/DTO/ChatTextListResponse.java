package com.turtledoctor.kgu.chatbot.chattext.DTO;

import com.turtledoctor.kgu.entity.enums.ChatRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatTextListResponse {

    ChatRole role;

    String content;

}
