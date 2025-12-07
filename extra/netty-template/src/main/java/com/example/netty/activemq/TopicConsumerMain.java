package com.example.netty.activemq;

import javax.jms.JMSException;

/**
 * Main application for ActiveMQ Consumer
 * Listens for messages from ActiveMQ topic
 */
public class TopicConsumerMain {

    public static void main(String[] args) {
        System.out.println("=== ActiveMQ Consumer ===");
        System.out.println("Broker URL: tcp://localhost:61616");
        System.out.println("Username: admin");
        System.out.println("Password: admin");
        System.out.println("Topic: sample.topic");
        System.out.println("=========================\n");

        ActiveMQConsumer consumer = null;
        
        // Add shutdown hook for graceful cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\nShutdown signal received, cleaning up...");
            ActiveMQGateway.getInstance().shutdown();
        }));
        
        try {
            consumer = new ActiveMQConsumer("sample.topic", true);
            consumer.startListening();
            
            System.out.println("✓ Consumer started successfully");
            System.out.println("✓ Listening for messages on topic: sample.topic");
            System.out.println("Press Ctrl+C to stop\n");
            
            // Keep running indefinitely
            while (true) {
                Thread.sleep(1);
            }
            
        } catch (JMSException e) {
            System.err.println("✗ Consumer error: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("\nConsumer interrupted");
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            System.out.println("Consumer shutdown complete");
        }
    }
}
