package com.turtledoctor.kgu.chatbot.openai.service;

import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiResponse;
import com.turtledoctor.kgu.chatbot.openai.repository.ChatHistoryRepository;
import com.turtledoctor.kgu.chatbot.openai.repository.ChatTextRepository;
import com.turtledoctor.kgu.chatbot.openai.repository.TempMemberRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatBotService {

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

    public void makeChatHistory(Long kakaoId, String prompt){
        Member member = tempMemberRepository.findByKakaoId(kakaoId);

        ChatText chatText = makeChatText(prompt, ChatRole.USER.getChatRole());


        ChatHistory chatHistory = ChatHistory.builder()
                .chatTextList((new ArrayList<>()))
                .member(member)
                .title(prompt)
                .build();

        chatHistory.getChatTextList().add(chatText);

        chatHistoryRepository.save(chatHistory);
    }

    public ChatText makeChatText(String body, String role){

        ChatText chatText = ChatText.builder()
                .chatRole(ChatRole.valueOf(role))
                .body(body).build();

        chatTextRepository.save(chatText);

        return chatText;
    }


    public void createTempMember(){
        tempMemberRepository.save(Member.builder()
                .kakaoId(1L)
                .chatHistoryList(new ArrayList<>())
                .commentList(new ArrayList<>())
                .replyList(new ArrayList<>())
                .role(UserRole.NORMAL)
                .nickname("김태완")
                .postList(new ArrayList<>())
                .email("thkim@naver.com").build()
                );
    }
}