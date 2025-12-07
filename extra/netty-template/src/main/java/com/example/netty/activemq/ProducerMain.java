package com.example.netty.activemq;

import javax.jms.JMSException;

/**
 * Main application for ActiveMQ Producer
 * Sends messages to ActiveMQ queue
 */
public class ProducerMain {

    public static void main(String[] args) {
        System.out.println("=== ActiveMQ Producer ===");
        System.out.println("Broker URL: tcp://localhost:61616");
        System.out.println("Username: admin");
        System.out.println("Password: admin");
        System.out.println("Queue: sample.queue");
        System.out.println("========================\n");

        ActiveMQProducer producer = null;
        try {
            producer = new ActiveMQProducer();
            
            // Send sample messages
            System.out.println("Sending messages...\n");
            
            producer.sendMessage("Hello from ActiveMQ Producer!");
            Thread.sleep(300);
            
            producer.sendMessage("This is message #2");
            Thread.sleep(300);
            
            producer.sendMessage("Important alert!", "priority", "high");
            Thread.sleep(300);
            
            // Send batch messages
            String[] batchMessages = {
                "Batch message 1",
                "Batch message 2",
                "Batch message 3"
            };
            producer.sendMessages(batchMessages);
            
            System.out.println("\n✓ All messages sent successfully!");
            
            // Optional: Keep sending messages periodically
            if (args.length > 0 && args[0].equalsIgnoreCase("continuous")) {
                System.out.println("\nContinuous mode: Sending messages every 5 seconds...");
                System.out.println("Press Ctrl+C to stop\n");
                
                int counter = 1;
                while (true) {
                    Thread.sleep(5000);
                    producer.sendMessage("Periodic message #" + counter);
                    counter++;
                }
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
