package com.turtledoctor.kgu.chatbot.openai.service;

import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAiApiService {


    @Value("${openai.api_key}")
    private String api_key;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiURL;

    @Autowired
    private final RestTemplate template;

    public Object sendPromptToOpenAi(String prompt){
        OpenAiRequest request = new OpenAiRequest(model, prompt);
        OpenAiResponse chatGPTResponse =  template.postForObject(apiURL, request, OpenAiResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage();
    }

}