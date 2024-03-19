package com.turtledoctor.kgu.chatbot.openai.controller;

import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiRequest;
import com.turtledoctor.kgu.chatbot.openai.DTO.OpenAiResponse;
import com.turtledoctor.kgu.chatbot.openai.service.OpenAiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/ChatBot")
public class OpenAiApiController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiURL;

    private final RestTemplate template;

    private final OpenAiApiService openAiApiService;

    @GetMapping("/prompt")
    @ResponseBody
    public Map<String, Object> chat(@RequestParam(name = "prompt")String prompt){
        Map<String,Object> result = new HashMap<>();
        result.put("answer", openAiApiService.sendPromptToOpenAi(prompt));
        return result;
    }

    @GetMapping("/api_key")
    @ResponseBody
    public Map<String, Object> returnApiKey(){
        Map<String, Object> result = new HashMap<>();
        //result.put("key",openAiApiService.returnKey());
        return result;
    }

}
