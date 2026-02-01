package com.example.springboottemplate.service.activemq;

import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 * Singleton wrapper for ActiveMQConsumer
 * Ensures only one consumer instance exists for the default queue
 */
public class ActiveMQConsumerSingleton {
    
    // Singleton instance - lazy initialization with thread safety
    private static volatile ActiveMQConsumerSingleton instance;
    
    // The actual consumer instance
    private ActiveMQConsumer consumer;
    
    /**
     * Private constructor to prevent external instantiation
     */
    private ActiveMQConsumerSingleton() throws JMSException {
        // Initialize with default queue
        consumer = new ActiveMQConsumer();
    }
    
    /**
     * Get the singleton instance (thread-safe, double-checked locking)
     */
    public static ActiveMQConsumerSingleton getInstance() throws JMSException {
        if (instance == null) {
            synchronized (ActiveMQConsumerSingleton.class) {
                if (instance == null) {
                    instance = new ActiveMQConsumerSingleton();
                }
            }
        }
        return instance;
    }

    public void resetSession() throws JMSException {
        instance = new ActiveMQConsumerSingleton();
    }
    
    /**
     * Receive a single message (blocking with timeout)
     * @param timeout timeout in milliseconds (0 = wait forever)
     */
    public String receiveMessage(long timeout) throws JMSException {
        return consumer.receiveMessage(timeout);
    }
    
    /**
     * Receive a single message (blocking, wait forever)
     */
    public String receiveMessage() throws JMSException {
        return consumer.receiveMessage();
    }
    
    /**
     * Receive message without blocking
     */
    public String receiveMessageNoWait() throws JMSException {
        return consumer.receiveMessageNoWait();
    }
    
    /**
     * Set up asynchronous message listener
     */
    public void setMessageListener(MessageListener listener) throws JMSException {
        consumer.setMessageListener(listener);
    }
    
    /**
     * Start listening with default message handler
     */
    public void startListening() throws JMSException {
        consumer.startListening();
    }
    
    /**
     * Close consumer and reset singleton instance
     */
    public void close() {
        if (consumer != null) {
            consumer.close();
        }
        synchronized (ActiveMQConsumerSingleton.class) {
            instance = null;
        }
    }
    
    /**
     * Example usage
     */
    public static void main(String[] args) {
        try {
            // Get singleton instance
            ActiveMQConsumerSingleton consumerSingleton = ActiveMQConsumerSingleton.getInstance();
            
            // Start listening for messages
            consumerSingleton.startListening();
            
            System.out.println("Singleton consumer listening. Press Ctrl+C to stop...");
            
            // Keep the application running
            Thread.sleep(60000); // Listen for 1 minute
            
        } catch (JMSException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                ActiveMQConsumerSingleton.getInstance().close();
                ActiveMQGateway.getInstance().closeConnection();
            } catch (JMSException e) {
                System.err.println("Error closing: " + e.getMessage());
            }
        }
    }
}
