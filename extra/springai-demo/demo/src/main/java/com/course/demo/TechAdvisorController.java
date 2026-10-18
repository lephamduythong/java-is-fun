package com.course.demo;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class TechAdvisorController {
    private final OpenAiChatModel chatModel;

    @Autowired
    public TechAdvisorController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping
    public String advise() {
        var message = chatModel.call("Whey protein là gì");

        return message;
    }
}
