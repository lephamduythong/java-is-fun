package com.example.springboottemplate.service.activemq;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveMQ Message Consumer
 * Receives messages from a queue or topic
 */
public class WonderQueueSrvConsumer {
    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageConsumer jmsConsumer;
    private Destination destination;
    private boolean isFirstLoad = true;

    /**
     * Initialize consumer with custom destination
     * @param destinationName Name of queue or topic
     * @param isTopic true for topic, false for queue
     */
    public WonderQueueSrvConsumer(String destinationName, boolean isTopic) throws JMSException {
        connection = WonderQueueSrv.getInstance().getConnection();
        // false: no transactions
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        if (isTopic) {
            destination = session.createTopic(destinationName);
        } else {
            destination = session.createQueue(destinationName);
        }
        
        jmsConsumer = session.createConsumer(destination);
        
        _logger.debug("Consumer initialized for: " + destinationName);
    }

    /**
     * Receive a single message (blocking with timeout)
     * @param timeout timeout in milliseconds (0 = wait forever)
     */
    public String receiveMessage(long timeout) throws JMSException {
        Message message = jmsConsumer.receive(timeout);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            _logger.debug("Message received: " + text);
            return text;
        }
        return null;
    }

    /**
     * Receive a single message (blocking, wait forever)
     */
    public String receiveMessage() throws JMSException {
        return receiveMessage(0);
    }

    /**
     * Receive message without blocking
     */
    public String receiveMessageNoWait() throws JMSException {
        Message message = jmsConsumer.receiveNoWait();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            _logger.debug("Message received: " + text);
            return text;
        }
        return null;
    }

    /**
     * Set up asynchronous message listener
     */
    public void setMessageListener(MessageListener listener) throws JMSException {
        jmsConsumer.setMessageListener(listener);
        _logger.debug("Message listener set up");
    }

    /**
     * Start listening with default message handler
     */
    public void startListening() throws JMSException {
        if (jmsConsumer == null) {
            throw new JMSException("Consumer not initialized");
        }
        if (!WonderQueueSrv.getInstance().checkConnection()) {
            throw new JMSException("ActiveMQ connection is not active");
        }
        if (isFirstLoad) {
            _logger.debug("First load - setting up listener");
            setListener();
            isFirstLoad = false;
        }
        _logger.debug("Started listening for messages from ActiveMQ server...");
    }

    private void setListener() throws JMSException {
        jmsConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        _logger.debug("Async message received from ActiveMQ server: " + text);
                        
                        // Check for custom properties
                        if (message.propertyExists("priority")) {
                            String priority = message.getStringProperty("priority");
                            _logger.debug("  Priority: " + priority);
                        }
                    }
                } catch (JMSException e) {
                    System.err.println("Error processing message from ActiveMQ server: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Close consumer and session
     */
    public void close() {
        try {
            if (jmsConsumer != null) {
                jmsConsumer.close();
            }
            if (session != null) {
                session.close();
            }
            _logger.debug("Consumer closed");
        } catch (JMSException e) {
            System.err.println("Error closing consumer: " + e.getMessage());
        }
    }

    // /**
    //  * Example usage - Synchronous receive
    //  */
    // public static void exampleSyncReceive() {
    //     WonderQueueSrvConsumer consumer = null;
    //     try {
    //         consumer = new WonderQueueSrvConsumer();
            
    //         _logger.debug("Waiting for messages (5 second timeout)...");
            
    //         // Receive up to 5 messages with 5 second timeout
    //         for (int i = 0; i < 5; i++) {
    //             String message = consumer.receiveMessage(5000);
    //             if (message == null) {
    //                 _logger.debug("No more messages");
    //                 break;
    //             }
    //         }
            
    //     } catch (JMSException e) {
    //         System.err.println("Error: " + e.getMessage());
    //         e.printStackTrace();
    //     } finally {
    //         if (consumer != null) {
    //             consumer.close();
    //         }
    //         WonderQueueSrv.getInstance().closeConnection();
    //     }
    // }

    // /**
    //  * Example usage - Asynchronous listener
    //  */
    // public static void exampleAsyncListener() {
    //     WonderQueueSrvConsumer consumer = null;
    //     try {
    //         consumer = new WonderQueueSrvConsumer();
            
    //         // Start listening
    //         consumer.startListening();
            
    //         _logger.debug("Listening for messages. Press Ctrl+C to stop...");
            
    //         // Keep the application running
    //         Thread.sleep(60000); // Listen for 1 minute
            
    //     } catch (JMSException | InterruptedException e) {
    //         System.err.println("Error: " + e.getMessage());
    //         e.printStackTrace();
    //     } finally {
    //         if (consumer != null) {
    //             consumer.close();
    //         }
    //         WonderQueueSrv.getInstance().closeConnection();
    //     }
    // }

    /**
     * Main method - runs async listener example
     */
    public static void main(String[] args) {
        _logger.debug("Starting ActiveMQ Consumer (Async mode)...");
        // exampleAsyncListener();
        
        // To run sync mode instead, uncomment:
        // exampleSyncReceive();
    }
}
