package com.course.demo;

// import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class TechAdvisorController {
    // private final ChatClient chatClient;
    private final OpenAiChatModel chatModel;

    // @Autowired 
    // public TechAdvisorController(ChatClient chatClient) {
    //     this.chatClient = chatClient;
    // }

    @Autowired 
    public TechAdvisorController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // @GetMapping
    // public String advise() {
    //     // Implement your logic to get tech advice using chatClient

    //     var message = chatClient
    //         .prompt()
    //         .user("Chào bạn Gehihi cute nhé")
    //         .call().content();

    //     return message;
    // }

    @GetMapping
    public String advise2() {
        // Implement your logic to get tech advice using chatClient

        var message = chatModel.call("Chào bạn đáng yêu nhé");

        return message;
    }
}
