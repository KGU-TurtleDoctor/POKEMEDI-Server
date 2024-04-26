package com.turtledoctor.kgu.chatbot.openai.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ChatBotApiResponse {
    Long chatHistoryId;
    String role;
    String content;

}
