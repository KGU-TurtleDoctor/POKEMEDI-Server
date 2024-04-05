package com.turtledoctor.kgu.chatbot.chathistory.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class ChatHistoryListResponse {

    Long chatHistoryId;
    String Title;
}
