package com.example.netty.activemq;

import javax.jms.JMSException;

/**
 * Main application for ActiveMQ Producer
 * Sends messages to ActiveMQ topic
 */
public class TopicProducerMain {

    public static void main(String[] args) {
        System.out.println("=== ActiveMQ Producer ===");
        System.out.println("Broker URL: tcp://localhost:61616");
        System.out.println("Username: admin");
        System.out.println("Password: admin");
        System.out.println("Topic: sample.topic");
        System.out.println("========================\n");

        ActiveMQProducer producer = null;
        try {
            producer = new ActiveMQProducer("sample.topic", true);
            
            // Send sample messages
            System.out.println("Sending messages...\n");
            
            producer.sendMessage("Hello from ActiveMQ Producer!");
            Thread.sleep(300);
            
            producer.sendMessage("This is message #2");
            Thread.sleep(300);
            
            producer.sendMessage("Important alert!", "priority", "high");
            Thread.sleep(300);

            // Generate 500 messages
            for (int i = 1; i <= 500; i++) {
                producer.sendMessage("Generated message #" + i);
            }
        } catch (JMSException e) {
            System.err.println("✗ Producer error: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("\nProducer interrupted");
        } finally {
            if (producer != null) {
                producer.close();
            }
            ActiveMQConfig.getInstance().shutdown();
            System.out.println("\nProducer shutdown complete");
        }
    }
}
