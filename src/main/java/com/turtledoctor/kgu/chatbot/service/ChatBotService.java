package com.turtledoctor.kgu.chatbot.service;

import com.turtledoctor.kgu.chathistory.DTO.ChatHistoryListResponse;
import com.turtledoctor.kgu.chathistory.service.ChatHistoryService;
import com.turtledoctor.kgu.chattext.DTO.ChatTextListResponse;
import com.turtledoctor.kgu.chattext.service.ChatTextService;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.openai.service.OpenAiApiService;
import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.ChatText;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.enums.ChatRole;
import com.turtledoctor.kgu.entity.repository.MemberRepository;
import com.turtledoctor.kgu.error.DTO.ValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatBotService {

    //Openai Api 관련 서비스
    private final OpenAiApiService openAiApiService;

    //ChatText 관련 서비스
    private final ChatTextService chatTextService;

    //ChatHistory 관련 서비스
    private final ChatHistoryService chatHistoryService;

    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public List<ChatHistoryListResponse> findChatHistoriesByMember(Long kakaoId){

        Member member = memberRepository.findBykakaoId(kakaoId.toString());

        List<ChatHistoryListResponse> result = chatHistoryService.findChatHistoryList(member);

        return result;
    }

    private ChatHistory updateChatHistory(ChatHistory chatHistory){
        return chatHistoryService.updateChatHistory(chatHistory);
    }

    private Long createChatHistory(Long kakaoId, String prompt){
        Member member = memberRepository.findBykakaoId(kakaoId.toString());

        return chatHistoryService.insertChatHistory(member, prompt);
    }

    private ChatHistory findChatHistoryById(Long chatHistoryId){
        return chatHistoryService.findChatHistory(chatHistoryId);
    }

    private ChatText createChatText(String prompt,ChatRole role,ChatHistory chatHistory){
        return chatTextService.createChatText(prompt, role, chatHistory);
    }

    private ChatBotApiResponse sendPromptToOpenAi(String prompt){
        return openAiApiService.sendPromptToOpenAi(prompt);
    }

    @Transactional
    public ChatBotApiResponse getResponseFromOpenAi(ChatBotApiRequest chatBotApiRequest){


        String prompt = chatBotApiRequest.getPrompt();
        Long chatHistoryId = chatBotApiRequest.getChatHistoryId();
        Long kakaoId = chatBotApiRequest.getKakaoId();
        ChatBotApiResponse chatBotApiResponse;
        //kakaoId가 있을때만 해당 대화를 따로 저장을 함
        if(kakaoId!=null){
            //chatHistoryId 가 없음 == 첫 질문임으로 chatHistory를 생성
            if(chatBotApiRequest.getChatHistoryId()==null){
                chatBotApiRequest.setChatHistoryId(createChatHistory(kakaoId,prompt));
                chatHistoryId = chatBotApiRequest.getChatHistoryId();
            }

            ChatHistory chatHistory = findChatHistoryById(chatHistoryId);

            //사용자의 채팅을 저장
            ChatText userChatText = createChatText(prompt, ChatRole.USER,chatHistory);

            //해당 채팅을 chatHistory에 매핑
            chatHistory.getChatTextList().add(userChatText);

            //채팅 히스토리 업데이트
            updateChatHistory(chatHistory);
            //prompt에 따른 답변 생성
            chatBotApiResponse = sendPromptToOpenAi(prompt);


            ChatText chatBotchatText =  createChatText(chatBotApiResponse.getContent(), ChatRole.CHATBOT,chatHistory);

            chatHistory.getChatTextList().add(chatBotchatText);

            updateChatHistory(chatHistory);
        }
        else{
            chatBotApiResponse = sendPromptToOpenAi(prompt);
        }


        //만약 로그인을 안했으면 null, 했으면 생성 or 넘어왔던 chatHistoryId를 반환
        chatBotApiResponse.setChatHistoryId(chatBotApiRequest.getChatHistoryId());

        return chatBotApiResponse;
    }

    @Transactional(readOnly = true)
    public List<ChatTextListResponse> findChatTextListByHisotoryID(Long chatHistoryId) throws ValidException {
        ChatHistory chatHistory = chatHistoryService.findChatHistory(chatHistoryId);

        List<ChatTextListResponse> result = new ArrayList<>();
        for(ChatText chatText : chatHistory.getChatTextList()){
            result.add(ChatTextListResponse.builder().content(chatText.getBody()).role(chatText.getChatRole()).build());
        }

        return result;
    }


}
