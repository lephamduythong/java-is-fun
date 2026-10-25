package com.course.demo;

import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class TechAdvisorController {
    // private final ChatClient chatClient;
    private final ChatClient chatClient;
    private final ChatClient chatClientWithPersona;

    @Autowired 
    public TechAdvisorController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
        this.chatClientWithPersona = builder.build();
    }

    @GetMapping("/default-system-prompt")
    public String defaultSystemPrompt() {
        return chatClient
            .prompt()
            .system("Bạn là một dev Java")
            .user("Giải thích cơ chế memory của Java")
            .call().content();
    }

    @GetMapping("/system-prompt-with-persona")
    public String systemPromptWithPersona(@RequestParam String persona, @RequestParam String question) {
        return chatClientWithPersona
            .prompt()
            .system("Bạn là một dev " + persona + " vui lòng format câu trả lời mardown đẹp lại, có xuống dòng")
            .user(question)
            .call().content();
    }
}
