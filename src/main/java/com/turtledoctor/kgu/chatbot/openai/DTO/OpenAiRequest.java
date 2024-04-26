package com.turtledoctor.kgu.chatbot.openai.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenAiRequest {
    private String model;
    private List<Message> messages;

    public OpenAiRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
