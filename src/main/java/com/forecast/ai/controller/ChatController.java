package com.forecast.ai.controller;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatModel model;

    @GetMapping("/openapi")
    public Object retrieveAiResponse(@RequestParam(name = "message") String message) {
        return model.chat(message);
    }

}
