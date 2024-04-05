package com.turtledoctor.kgu.chatbot.controller;

import com.turtledoctor.kgu.chatbot.chathistory.DTO.ChatHistoryListRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.chattext.DTO.ChatTextListRequest;
import com.turtledoctor.kgu.chatbot.service.ChatBotService;
import com.turtledoctor.kgu.error.DTO.ErrorMessage;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatBotController {


    private final ChatBotService chatBotService;

    @PostMapping("/prompt")
    public ResponseEntity<ResponseDTO> chatting(@RequestBody ChatBotApiRequest chatBotApiRequest){


        ChatBotApiResponse chatBotApiResponse;
        chatBotApiResponse = chatBotService.getResponseFromOpenAi(chatBotApiRequest);


        ResponseDTO responseDTO = ResponseDTO.builder().result(chatBotApiResponse).isSuccess(true).stateCode(200).build();
        return ResponseEntity.ok().body(responseDTO);
    }



    @PostMapping("/chathistory/chattextlist")
    public ResponseEntity<ResponseDTO> findChatTextFromChatHistory(@RequestBody ChatTextListRequest chatTextListRequest){

        ResponseDTO responseDTO = ResponseDTO.builder()
                .stateCode(200)
                .isSuccess(true)
                .result(chatBotService.findChatTextListByHisotoryID(chatTextListRequest.getChatHistoryId())).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/chathistory/chathistorylist")
    public ResponseEntity<ResponseDTO> findChatHistory(@RequestBody ChatHistoryListRequest chatHistoryListRequest/*이후 쿠키에서 뽑아내는것으로 변경*/){
        Long kakaoId = chatHistoryListRequest.getKakaoId();
        ResponseDTO responseDTO;
        try{
            responseDTO = ResponseDTO.builder().result(chatBotService.findChatHistoriesByMember(kakaoId)).isSuccess(true).stateCode(200).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch (NullPointerException e){
            responseDTO = ResponseDTO.builder().stateCode(HttpStatus.BAD_REQUEST.value()).isSuccess(false)
                    .result(ErrorMessage.builder().errorMessage("회원 정보가 없습니다.").build()).build();
            return ResponseEntity.status(400).body(responseDTO);
        }
    }

}
