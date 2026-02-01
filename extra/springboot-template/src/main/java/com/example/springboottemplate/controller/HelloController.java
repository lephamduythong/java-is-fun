package com.example.springboottemplate.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboottemplate.service.activemq.WonderQueueSrvProducer;
import com.example.springboottemplate.service.jms.JmsGateway;
import com.example.springboottemplate.service.jms.JmsProducer;
import com.example.springboottemplate.service.other.JmsQueueExample;

@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    @GetMapping("/")
    public String hello() {
        logger.info("Hello endpoint was called");
        logger.debug("This is a DEBUG level log message");
        return "Hello, Spring Boot with Java 11!";
    }

    @GetMapping("/log-demo")
    public String logDemo(@RequestParam(defaultValue = "World") String name) {
        logger.trace("TRACE level log - Entering logDemo method");
        logger.debug("DEBUG level log - Parameter received: {}", name);
        logger.info("INFO level log - Processing request for name: {}", name);
        logger.warn("WARN level log - This is a warning message");
        
        try {
            // Simulate a potential error scenario
            if ("error".equalsIgnoreCase(name)) {
                throw new RuntimeException("Simulated error for demonstration");
            }
        } catch (Exception e) {
            logger.error("ERROR level log - An error occurred: {}", e.getMessage(), e);
        }
        
        logger.info("Successfully processed request for: {}", name);
        return String.format("Log demo completed! Check logs folder for output. Hello, %s!", name);
    }

    @GetMapping("/activemq-queue-test")
    public String queueTest() {
        WonderQueueSrvProducer producer = null;
        try {

            logger.info("==1");

            // Create producer
            producer = new WonderQueueSrvProducer("thong.topic.request", true);
            
            logger.info("==2");

            // Send single message
            producer.sendMessage("Hello from ActiveMQ!");

            logger.info("==3");
            
            // Send message with property
            producer.sendMessage("Important message", "priority", "high");
            
            // Send multiple messages
            String[] messages = {
                "Message 1",
                "Message 2",
                "Message 3"
            };
            producer.sendMessages(messages);
            
            System.out.println("All messages sent successfully!");
            
        } catch (JMSException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return "Error sending messages: " + e.getMessage();
        } finally {
            if (producer != null) {
                producer.close();
            }
        }

        return "Queue test completed! Check ActiveMQ broker for messages.";
    }

    /**
     * Send a message to WildFly JMS Queue (MyTestQueue)
     * Example: http://localhost:5000/send-jms?message=Hello
     */
    @GetMapping("/send-jms")
    public String sendJmsMessage(@RequestParam(defaultValue = "Hello WildFly JMS!") String message) {
        logger.info("Sending message to WildFly JMS Queue: {}", message);
        
        JmsQueueExample jmsExample = new JmsQueueExample();
        
        try {
            String result = jmsExample.sendMessage(message);
            logger.info("JMS message sent successfully");
            return result;
        } catch (Exception e) {
            logger.error("Error sending JMS message: {}", e.getMessage(), e);
            return "Error sending message to JMS Queue: " + e.getMessage();
        }
    }

    @GetMapping("/send-jms-2")
    public String sendJmsMessage2(@RequestParam(defaultValue = "Hello WildFly JMS!") String message)  {
        logger.info("Sending message to WildFly JMS Queue 2: {}", message);
        
        try {
            var jmsService = new JmsProducer();
            jmsService.sendMessage(message);
            logger.info("JMS message sent successfully");
        } catch (Exception e) {
            logger.error("Error sending JMS message: {}", e.getMessage(), e);
            return "Error sending message to JMS Queue: " + e.getMessage();
        }

        return "Message sent to JMS Queue 2 successfully!";
    }

    @GetMapping(value = "/read-html", produces = MediaType.TEXT_HTML_VALUE)
    public String readHtml() {
        logger.info("Reading HTML file from resources");
        
        try {
            ClassPathResource resource = new ClassPathResource("static/html/test.html");
            InputStream inputStream = resource.getInputStream();
            String htmlContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            
            logger.info("HTML file read successfully");
            return htmlContent;
            
        } catch (IOException e) {
            logger.error("Error reading HTML file: {}", e.getMessage(), e);
            return "<html><body><h1>Error</h1><p>Could not read HTML file: " + e.getMessage() + "</p></body></html>";
        }
    }

}
