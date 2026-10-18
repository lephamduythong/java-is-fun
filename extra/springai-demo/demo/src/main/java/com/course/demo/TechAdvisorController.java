package com.course.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class TechAdvisorController {
    private final ChatClient chatClient;

    @Autowired 
    public TechAdvisorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping
    public String advise() {
        // Implement your logic to get tech advice using chatClient

        var message = chatClient
            .prompt()
            .user("Chào bạn Gehihi cute nhé")
            .call().content();

        return message;
    }
}
