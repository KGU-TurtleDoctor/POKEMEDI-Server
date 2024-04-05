package com.turtledoctor.kgu.chatbot.openai.service;

import com.turtledoctor.kgu.chatbot.chathistory.DTO.ChatHistoryListResponse;
import com.turtledoctor.kgu.chatbot.chattext.DTO.ChatTextListResponse;
import com.turtledoctor.kgu.chatbot.openai.DTO.*;
import com.turtledoctor.kgu.chatbot.chathistory.repository.ChatHistoryRepository;
import com.turtledoctor.kgu.chatbot.chattext.repository.ChatTextRepository;
import com.turtledoctor.kgu.testPackage.repository.TempMemberRepository;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.ChatText;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.enums.ChatRole;
import com.turtledoctor.kgu.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAiApiService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatTextRepository chatTextRepository;

    //임시 멤버 레포지토리
    private final TempMemberRepository tempMemberRepository;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiURL;

    private final RestTemplate template;

    public ChatBotApiResponse sendPromptToOpenAi(String prompt){
        OpenAiRequest request = new OpenAiRequest(model, prompt);
        OpenAiResponse chatGPTResponse =  template.postForObject(apiURL, request, OpenAiResponse.class);

        ChatBotApiResponse result = ChatBotApiResponse.builder()
                                .role(ChatRole.CHATBOT.getChatRole())
                                .content(chatGPTResponse.getChoices().get(0).getMessage().getContent())
                                .build();
        return result;
    }



    public ChatHistory findChatHistory(Long chatHistoryId){
        return chatHistoryRepository.findById(chatHistoryId).get();
    }

    public void updateChatHistory(ChatHistory chatHistory){
        chatHistoryRepository.save(chatHistory);
    }










}