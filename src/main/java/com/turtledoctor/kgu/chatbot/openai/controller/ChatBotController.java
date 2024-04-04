package com.turtledoctor.kgu.chatbot.openai.controller;

import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.openai.service.ChatBotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatBotController {


    private final ChatBotService chatBotService;

    @PostMapping("/prompt")
    public ResponseEntity<ChatBotApiResponse> chat(@RequestBody ChatBotApiRequest chatBotApiRequest){
        if(chatBotApiRequest.getChatHistoryId()==null){
            //추후 chatHistory를 만들어 반환하는 것으로 변환
            chatBotApiRequest.setChatHistoryId(123L);
        }
        ChatBotApiResponse chatBotApiResponse = chatBotService.sendPromptToOpenAi(chatBotApiRequest.getPrompt());
        chatBotApiResponse.setChatHistoryId(chatBotApiRequest.getChatHistoryId());
        return ResponseEntity.ok().body(chatBotApiResponse);
    }


}
