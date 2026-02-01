package com.example.springboottemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.springboottemplate.service.activemq.ActiveMQConsumer;
import com.example.springboottemplate.service.jms.JmsConsumer;

@SpringBootApplication
public class SpringbootTemplateApplication extends SpringBootServletInitializer {

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootTemplateApplication.class);
    }

    /**
     * Start JMS Consumer when application starts
     */
    @Bean
    public CommandLineRunner startJmsConsumer() {
        return args -> {
            try {
                _logger.info("Starting JMS Consumer...");
                JmsConsumer consumer = new JmsConsumer();
                consumer.startListening();
                _logger.info("JMS Consumer started successfully and listening for messages");
                
                // Register shutdown hook to close consumer gracefully
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    _logger.info("Shutting down JMS Consumer...");
                    consumer.close();
                }));
                
            } catch (Exception e) {
                _logger.error("Failed to start JMS Consumer: " + e.getMessage(), e);
            }
        };
    }

    /**
     * Start ActiveMQ Consumer when application starts
     */
    @Bean
    public CommandLineRunner startActiveMQConsumer() {
        return args -> {
            try {
                _logger.info("Starting ActiveMQ Consumer...");
                ActiveMQConsumer consumer = new ActiveMQConsumer();
                consumer.startListening();
                _logger.info("ActiveMQ Consumer started successfully and listening for messages");
                
                // Register shutdown hook to close consumer gracefully
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    _logger.info("Shutting down ActiveMQ Consumer...");
                    consumer.close();
                }));
                
            } catch (Exception e) {
                _logger.error("Failed to start ActiveMQ Consumer: " + e.getMessage(), e);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

}
