package com.turtledoctor.kgu.chatbot.openai.controller;

import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiResponse;
import com.turtledoctor.kgu.chatbot.openai.service.OpenAiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class OpenAiApiController {


    private final OpenAiApiService openAiApiService;

    @PostMapping("/prompt")
    public ResponseEntity<ChatBotApiResponse> chat(@RequestBody ChatBotApiRequest chatBotApiRequest){
        if(chatBotApiRequest.getChatHistoryId()==null){
            //추후 chatHistory를 만들어 반환하는 것으로 변환
            chatBotApiRequest.setChatHistoryId(123L);
        }
        ChatBotApiResponse chatBotApiResponse = openAiApiService.sendPromptToOpenAi(chatBotApiRequest.getPrompt());
        chatBotApiResponse.setChatHistoryId(chatBotApiRequest.getChatHistoryId());
        return ResponseEntity.ok().body(chatBotApiResponse);
    }

}
