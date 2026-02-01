package com.example.springboottemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.springboottemplate.service.activemq.ActiveMQConsumer;
import com.example.springboottemplate.service.activemq.ActiveMQConsumerSingleton;
import com.example.springboottemplate.service.jms.JmsConsumer;

@SpringBootApplication
public class SpringbootTemplateApplication extends SpringBootServletInitializer {

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootTemplateApplication.class);
    }

    // /**
    //  * Start JMS Consumer when application starts
    //  */
    // @Bean
    // public CommandLineRunner startJmsConsumer() {
    //     return args -> {
    //         try {
    //             _logger.info("Starting JMS Consumer...");
    //             JmsConsumer consumer = new JmsConsumer();
    //             consumer.startListening();
    //             _logger.info("JMS Consumer started successfully and listening for messages");
                
    //             // Register shutdown hook to close consumer gracefully
    //             Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    //                 _logger.info("Shutting down JMS Consumer...");
    //                 consumer.close();
    //             }));
                
    //         } catch (Exception e) {
    //             _logger.error("Failed to start JMS Consumer: " + e.getMessage(), e);
    //         }
    //     };
    // }

    // /**
    //  * Start ActiveMQ Consumer when application starts
    //  */
    // @Bean
    // public CommandLineRunner startActiveMQConsumer() {
    //     return args -> {
    //         ExecutorService executor = Executors.newSingleThreadExecutor();
    //         executor.submit(() -> {
    //             runJob(executor);
    //         });
    //     };
    // }

    // private void runJob(ExecutorService executor) {
    //     try {
    //         var consumer = ActiveMQConsumerSingleton.getInstance();
    //         while (!Thread.currentThread().isInterrupted()) {
    //             Thread.sleep(5000); // Delay
    //             _logger.info("Starting ActiveMQ Consumer...");
    //             consumer.startListening();
    //             _logger.info("ActiveMQ Consumer started successfully and listening for messages");
    //             // Register shutdown hook to close consumer gracefully
    //             Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    //                 _logger.info("Shutting down ActiveMQ Consumer...");
    //                 consumer.close();
    //             }));
    //         }
    //     } catch (Exception e) {
    //         ActiveMQConsumerSingleton consumer = null;
    //         try {
    //             consumer = ActiveMQConsumerSingleton.getInstance();
    //         } catch (JMSException e1) {
    //             _logger.error("Consumer error init: " + e1.getMessage(), e1);
    //         }
    //         _logger.error("Failed to start ActiveMQ Consumer: " + e.getMessage(), e);
    //         if (consumer != null) {
    //             consumer.close();
    //             try {
    //                 consumer.resetSession();
    //             } catch (JMSException e1) {
    //                 _logger.error("Failed to reset ActiveMQ Consumer session: " + e1.getMessage(), e1);
    //             }
    //             executor.submit(() -> {
    //                 runJob(executor);
    //             });
    //         }
    //     }
    // }


    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

}
