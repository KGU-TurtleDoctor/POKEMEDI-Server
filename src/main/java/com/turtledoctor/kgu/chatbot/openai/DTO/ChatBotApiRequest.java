package com.turtledoctor.kgu.chatbot.openai.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatBotApiRequest {
    Long chatHistoryId;
    String prompt;
    Long kakaoId;
}
