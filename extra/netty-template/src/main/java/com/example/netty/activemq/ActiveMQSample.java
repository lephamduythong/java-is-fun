package com.example.netty.activemq;

import javax.jms.JMSException;

/**
 * Sample application demonstrating ActiveMQ Producer and Consumer
 */
public class ActiveMQSample {

    /**
     * Run producer and consumer examples
     */
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("=== ActiveMQ Classic Sample ===");
            System.out.println("Broker URL: tcp://localhost:61616");
            System.out.println("Username: admin");
            System.out.println("Password: admin");
            System.out.println("Queue: sample.queue");
            System.out.println("================================\n");

            // Check if argument is provided
            if (args.length > 0) {
                String mode = args[0].toLowerCase();
                
                if (mode.equals("producer")) {
                    runProducer();
                } else if (mode.equals("consumer")) {
                    runConsumer();
                } else if (mode.equals("both")) {
                    runBoth();
                } else {
                    printUsage();
                }
            } else {
                // Default: run both
                runBoth();
            }
        }
    }

    /**
     * Run only producer
     */
    private static void runProducer() {
        System.out.println(">>> Running PRODUCER only\n");
        ActiveMQProducer producer = null;
        try {
            producer = new ActiveMQProducer();
            
            // Send sample messages
            producer.sendMessage("Hello from ActiveMQ Producer!");
            producer.sendMessage("This is message #2");
            producer.sendMessage("Important alert!", "priority", "high");
            
            String[] batchMessages = {
                "Batch message 1",
                "Batch message 2",
                "Batch message 3"
            };
            producer.sendMessages(batchMessages);
            
            System.out.println("\n✓ All messages sent successfully!");
            
        } catch (JMSException e) {
            System.err.println("✗ Producer error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (producer != null) {
                producer.close();
            }
            ActiveMQConfig.getInstance().closeConnection();
        }
    }

    /**
     * Run only consumer
     */
    private static void runConsumer() {
        System.out.println(">>> Running CONSUMER only\n");
        ActiveMQConsumer consumer = null;
        try {
            consumer = new ActiveMQConsumer();
            consumer.startListening();
            
            System.out.println("✓ Listening for messages...");
            System.out.println("Press Ctrl+C to stop\n");
            
            // Keep running
            while (true) {
                Thread.sleep(1000);
            }
            
        } catch (JMSException | InterruptedException e) {
            System.err.println("✗ Consumer error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            ActiveMQConfig.getInstance().closeConnection();
        }
    }

    /**
     * Run both producer and consumer
     */
    private static void runBoth() {
        System.out.println(">>> Running BOTH Producer and Consumer\n");
        
        ActiveMQConsumer consumer = null;
        ActiveMQProducer producer = null;
        
        try {
            // Start consumer first
            System.out.println("1. Starting Consumer...");
            consumer = new ActiveMQConsumer();
            consumer.startListening();
            System.out.println("✓ Consumer listening\n");
            
            // Wait a moment for consumer to be ready
            Thread.sleep(1000);
            
            // Start producer
            System.out.println("2. Starting Producer...");
            producer = new ActiveMQProducer();
            
            // Send messages
            System.out.println("\n3. Sending messages...");
            producer.sendMessage("Test message 1");
            Thread.sleep(500);
            
            producer.sendMessage("Test message 2");
            Thread.sleep(500);
            
            producer.sendMessage("High priority message", "priority", "high");
            Thread.sleep(500);
            
            producer.sendMessage("Test message 3");
            Thread.sleep(500);
            
            System.out.println("\n✓ All messages sent!");
            System.out.println("\n4. Waiting for messages to be processed...");
            Thread.sleep(2000);
            
            System.out.println("\n✓ Demo completed successfully!");
            
        } catch (JMSException | InterruptedException e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (producer != null) {
                producer.close();
            }
            if (consumer != null) {
                consumer.close();
            }
            ActiveMQConfig.getInstance().closeConnection();
        }
    }

    /**
     * Print usage instructions
     */
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java com.example.netty.activemq.ActiveMQSample [mode]");
        System.out.println();
        System.out.println("Modes:");
        System.out.println("  producer  - Run only the message producer");
        System.out.println("  consumer  - Run only the message consumer");
        System.out.println("  both      - Run both producer and consumer (default)");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java com.example.netty.activemq.ActiveMQSample producer");
        System.out.println("  java com.example.netty.activemq.ActiveMQSample consumer");
        System.out.println("  java com.example.netty.activemq.ActiveMQSample both");
    }
}
