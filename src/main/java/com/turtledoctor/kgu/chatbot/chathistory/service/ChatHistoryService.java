package com.turtledoctor.kgu.chatbot.chathistory.service;

import com.turtledoctor.kgu.chatbot.chathistory.DTO.ChatHistoryListResponse;
import com.turtledoctor.kgu.chatbot.chathistory.repository.ChatHistoryRepository;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    public List<ChatHistoryListResponse> findChatHistoryList(Member member){

        List<ChatHistory> chatHistoryList = member.getChatHistoryList();

        List<ChatHistoryListResponse> result = new ArrayList<>();

        for(ChatHistory chatHistory : chatHistoryList){
            result.add(ChatHistoryListResponse.builder()
                    .chatHistoryId(chatHistory.getId())
                    .Title(chatHistory.getTitle()).build());
        }
        return result;
    }

    public Long insertChatHistory(Member member, String prompt){
        ChatHistory chatHistory = ChatHistory.builder()
                .chatTextList((new ArrayList<>()))
                .member(member)
                .title(prompt)
                .build();

        ChatHistory madeChatHistory = chatHistoryRepository.save(chatHistory);

        return madeChatHistory.getId();
    }

    public ChatHistory updateChatHistory(ChatHistory chatHistory){
        return chatHistoryRepository.save(chatHistory);
    }

    public ChatHistory findChatHistory(Long chatHistoryId){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatHistoryId).get();

        return chatHistory;
    }
}
