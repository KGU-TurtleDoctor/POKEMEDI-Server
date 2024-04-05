package com.turtledoctor.kgu.chatbot.openai.service;

import com.turtledoctor.kgu.chatbot.openai.DTO.*;
import com.turtledoctor.kgu.chatbot.chathistory.repository.ChatHistoryRepository;
import com.turtledoctor.kgu.chatbot.chattext.repository.ChatTextRepository;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.enums.ChatRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAiApiService {


    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiURL;

    private final RestTemplate template;

    public ChatBotApiResponse sendPromptToOpenAi(String prompt){
        OpenAiRequest request = new OpenAiRequest(model, prompt);
        OpenAiResponse chatGPTResponse =  template.postForObject(apiURL, request, OpenAiResponse.class);

        ChatBotApiResponse result = ChatBotApiResponse.builder()
                                .role(ChatRole.CHATBOT.toString())
                                .content(chatGPTResponse.getChoices().get(0).getMessage().getContent())
                                .build();
        return result;
    }
}