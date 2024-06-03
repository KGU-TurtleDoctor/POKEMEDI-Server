package com.turtledoctor.kgu.chattext.service;

import com.turtledoctor.kgu.chattext.DTO.ChatTextListResponse;
import com.turtledoctor.kgu.chattext.repository.ChatTextRepository;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.ChatText;
import com.turtledoctor.kgu.entity.enums.ChatRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatTextService {

    private final ChatTextRepository chatTextRepository;

    @Transactional(readOnly = true)
    public List<ChatTextListResponse> findChatTextList(ChatHistory chatHistory){

        List<ChatText> chatTextList = chatHistory.getChatTextList();
        List<ChatTextListResponse> result = new ArrayList<>();
        for (ChatText chatText : chatTextList) {
            result.add(ChatTextListResponse.builder()
                    .content(chatText.getBody())
                    .role(chatText.getChatRole().getCode())
                    .build());
        }

        return result;
    }

    @Transactional
    public ChatText createChatText(String body, ChatRole role, ChatHistory chatHistory){

        ChatText chatText = ChatText.builder()
                .chatRole(role)
                .body(body)
                .chatHistory(chatHistory).build();

        chatTextRepository.save(chatText);

        return chatText;
    }
}
