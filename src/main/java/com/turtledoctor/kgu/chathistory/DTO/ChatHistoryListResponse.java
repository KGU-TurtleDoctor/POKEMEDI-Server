package com.turtledoctor.kgu.chathistory.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatHistoryListResponse {

    Long chatHistoryId;
    String Title;
    String name;
    String date;

}
