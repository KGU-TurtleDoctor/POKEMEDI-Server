package com.turtledoctor.kgu.chathistory.service;

import com.turtledoctor.kgu.chathistory.DTO.ChatHistoryListResponse;
import com.turtledoctor.kgu.chathistory.repository.ChatHistoryRepository;
import com.turtledoctor.kgu.converter.DateConverter;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;


    @Transactional(readOnly = true)
    public ChatHistoryListResponse findChatHistoryOne(Member member){
        ChatHistory chatHistory = chatHistoryRepository.findTop1ByMemberOrderByCreatedAtDesc(member);

        ChatHistoryListResponse result = ChatHistoryListResponse.builder()
                .chatHistoryId(chatHistory.getId())
                .Title(chatHistory.getTitle())
                .date(DateConverter.ConverteDate(chatHistory.getCreatedAt()))
                .name(member.getName()).build();

        return result;
    }
    @Transactional(readOnly = true)
    public List<ChatHistoryListResponse> findChatHistoryList(Member member){

        List<ChatHistory> chatHistoryList = chatHistoryRepository.findChatHistoryByMemberOrderByUpdatedAtDesc(member);

        List<ChatHistoryListResponse> result = new ArrayList<>();

        for(ChatHistory chatHistory : chatHistoryList){
            result.add(ChatHistoryListResponse.builder()
                    .chatHistoryId(chatHistory.getId())
                    .Title(chatHistory.getTitle())
                    .date(DateConverter.ConverteDate(chatHistory.getCreatedAt()))
                    .name(member.getName()).build());
        }
        return result;
    }

    @Transactional
    public Long insertChatHistory(Member member, String prompt){
        ChatHistory chatHistory = ChatHistory.builder()
                .chatTextList((new ArrayList<>()))
                .member(member)
                .title(prompt)
                .build();

        ChatHistory madeChatHistory = chatHistoryRepository.save(chatHistory);

        return madeChatHistory.getId();
    }

    @Transactional
    public ChatHistory updateChatHistory(ChatHistory chatHistory){
        return chatHistoryRepository.save(chatHistory);
    }

    @Transactional(readOnly = true)
    public ChatHistory findChatHistory(Long chatHistoryId){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatHistoryId).get();

        return chatHistory;
    }
}
