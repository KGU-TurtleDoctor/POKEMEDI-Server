package com.turtledoctor.kgu.chatbot.controller;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.ChatBotApiResponse;
import com.turtledoctor.kgu.chatbot.service.ChatBotService;
import com.turtledoctor.kgu.error.DTO.ErrorMessage;
import com.turtledoctor.kgu.error.DTO.ValidException;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatBotController {

    @Value("${spring.jwt.secret}")
    String secret;
    JWTUtil jwtUtil;

    private final ChatBotService chatBotService;

    @PostMapping("/prompt")
    public ResponseEntity<ResponseDTO> chatting(@CookieValue(name = "Authorization") String author,@RequestBody ChatBotApiRequest chatBotApiRequest){
        jwtUtil = new JWTUtil(secret);
        if(author !=null){
            chatBotApiRequest.setKakaoId(Long.valueOf(jwtUtil.getkakaoId(author)));
        }
        ChatBotApiResponse chatBotApiResponse;
        chatBotApiResponse = chatBotService.getResponseFromOpenAi(chatBotApiRequest);


        ResponseDTO responseDTO = ResponseDTO.builder().result(chatBotApiResponse).isSuccess(true).stateCode(200).build();
        return ResponseEntity.ok().body(responseDTO);
    }



    @GetMapping("/chathistories/{chathistoryId}/chattexts")
    public ResponseEntity<ResponseDTO> findChatTextFromChatHistory(@CookieValue(name = "Authorization") String author,@PathVariable(name="chathistoryId") Long chatHistoryId){
        jwtUtil = new JWTUtil(secret);
        ResponseDTO responseDTO;
        try{
            responseDTO = ResponseDTO.builder()
                    .stateCode(200)
                    .isSuccess(true)
                    .result(chatBotService.findChatTextListByHisotoryID(chatHistoryId)).build();

        }catch (ValidException e){
            responseDTO = ResponseDTO.builder()
                    .stateCode(401)
                    .isSuccess(true)
                    .result(ErrorMessage.builder().errorMessage(e.getMessage()).build()).build();
        }

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/chathistories")
    public ResponseEntity<ResponseDTO> findChatHistory(@CookieValue(name = "Authorization") String author){

        jwtUtil = new JWTUtil(secret);

        Long kakaoId = Long.valueOf(jwtUtil.getkakaoId(author));
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

    @GetMapping("/chathsitory")
    public ResponseEntity<ResponseDTO> findChatHistoryOne(@CookieValue(name = "Authorization") String author){

        jwtUtil = new JWTUtil(secret);
        Long kakaoId = Long.valueOf(jwtUtil.getkakaoId(author));
        ResponseDTO responseDTO;
        try{
            responseDTO = ResponseDTO.builder().result(chatBotService.findChatHistoryByMember(kakaoId)).isSuccess(true).stateCode(200).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch (NullPointerException e){
            responseDTO = ResponseDTO.builder().stateCode(HttpStatus.BAD_REQUEST.value()).isSuccess(false)
                    .result(ErrorMessage.builder().errorMessage("회원 정보가 없습니다.").build()).build();
            return ResponseEntity.status(400).body(responseDTO);
        }
    }
}
