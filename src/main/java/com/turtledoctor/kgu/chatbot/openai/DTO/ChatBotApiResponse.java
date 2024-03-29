package com.turtledoctor.kgu.chatbot.openai.DTO;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatBotApiResponse {
    String role;
    String content;
}
