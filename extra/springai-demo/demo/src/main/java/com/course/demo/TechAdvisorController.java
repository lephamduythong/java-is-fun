package com.course.demo;

import java.io.IOException;
import java.util.Map;

import org.apache.tomcat.util.digester.DocumentProperties.Charset;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
// import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
    private final ChatClient chatClientWithPersona2;

    @Value("classpath:/prompts/recipe-prompt.st")
    private Resource recipePrompt;

    @Autowired 
    public TechAdvisorController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
        this.chatClientWithPersona = builder.build();
        this.chatClientWithPersona2 = builder.defaultSystem("Bạn là 1 đầu bếp kinh nghiệm nấu ăn, nhà dinh dưỡng hiểu về chế độ ăn cho người tập gym").build();
    }

    @GetMapping("/recipe-prompt")
    public String recipePrompt() throws IOException {
        
        var res = "<pre>" + chatClientWithPersona2.prompt(recipePrompt
            .getContentAsString(java.nio.charset.StandardCharsets.UTF_8))
            .user("Hãy viết thực đơn mẫu cho người bị skinny fat, cân nặng 62kg cao 1m67 tuổi 30")
            .call().content() + "</pre>";
        
        return res;
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

    @GetMapping("/prompt-varaiables")
    public String withPersona(@RequestParam String language, @RequestParam String task) {
        PromptTemplate template = new PromptTemplate("Write a short {language} code snippet that demonstrates {task}");
        Prompt prompt = template.create(Map.of("language", language, "task", task));
        return "<pre>" + chatClient.prompt(prompt).call().content() + "</pre>";
    }

    @GetMapping("/prompt-varaiables-vietnamese")
    public String promptVaraiablesVietnamese(@RequestParam String language, @RequestParam String task) {
        PromptTemplate template = new PromptTemplate("Write a short {language} code snippet that demonstrates {task}");
        Prompt prompt = template.create(Map.of("language", language, "task", task));
        return "<pre>" + chatClient
            .prompt(prompt)
            .system("Use returned result in Vietnamese language")
            .call().content() + "</pre>";
    }
}
