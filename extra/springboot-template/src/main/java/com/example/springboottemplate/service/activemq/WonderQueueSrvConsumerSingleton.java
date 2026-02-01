package com.example.springboottemplate.service.activemq;

import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 * Singleton wrapper for ActiveMQConsumer
 * Ensures only one consumer instance exists for the default queue
 */
public class WonderQueueSrvConsumerSingleton {
    
    private static final String QUEUE_NAME = "thong.queue.request";
    private static final boolean IS_TOPIC = false;

    // Singleton instance - lazy initialization with thread safety
    private static volatile WonderQueueSrvConsumerSingleton instance;
    
    // The actual consumer instance
    private WonderQueueSrvConsumer consumer;
    
    /**
     * Private constructor to prevent external instantiation
     */
    private WonderQueueSrvConsumerSingleton() throws JMSException {
        // Initialize with default queue
        consumer = new WonderQueueSrvConsumer(QUEUE_NAME, IS_TOPIC);
    }
    
    /**
     * Get the singleton instance (thread-safe, double-checked locking)
     */
    public static WonderQueueSrvConsumerSingleton getInstance() throws JMSException {
        if (instance == null) {
            synchronized (WonderQueueSrvConsumerSingleton.class) {
                if (instance == null) {
                    instance = new WonderQueueSrvConsumerSingleton();
                }
            }
        }
        return instance;
    }

    public void resetSession() throws JMSException {
        instance = new WonderQueueSrvConsumerSingleton();
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
        synchronized (WonderQueueSrvConsumerSingleton.class) {
            instance = null;
        }
    }
    
    /**
     * Example usage
     */
    public static void main(String[] args) {
        try {
            // Get singleton instance
            WonderQueueSrvConsumerSingleton consumerSingleton = WonderQueueSrvConsumerSingleton.getInstance();
            
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
                WonderQueueSrvConsumerSingleton.getInstance().close();
                WonderQueueSrv.getInstance().closeConnection();
            } catch (JMSException e) {
                System.err.println("Error closing: " + e.getMessage());
            }
        }
    }
}
