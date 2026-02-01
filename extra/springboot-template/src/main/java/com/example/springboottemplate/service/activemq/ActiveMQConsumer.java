package com.example.springboottemplate.service.activemq;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveMQ Message Consumer
 * Receives messages from a queue or topic
 */
public class ActiveMQConsumer {
    private static final String DEFAULT_QUEUE = "thong.queue.request";

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageConsumer jmsConsumer;
    private Destination destination;

    /**
     * Initialize consumer with default queue
     */
    public ActiveMQConsumer() throws JMSException {
        this(DEFAULT_QUEUE, false);
    }

    /**
     * Initialize consumer with custom destination
     * @param destinationName Name of queue or topic
     * @param isTopic true for topic, false for queue
     */
    public ActiveMQConsumer(String destinationName, boolean isTopic) throws JMSException {
        connection = ActiveMQGateway.getInstance().getConnection();
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
        _logger.debug("Started listening for messages from ActiveMQ server...");
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

    /**
     * Example usage - Synchronous receive
     */
    public static void exampleSyncReceive() {
        ActiveMQConsumer consumer = null;
        try {
            consumer = new ActiveMQConsumer();
            
            _logger.debug("Waiting for messages (5 second timeout)...");
            
            // Receive up to 5 messages with 5 second timeout
            for (int i = 0; i < 5; i++) {
                String message = consumer.receiveMessage(5000);
                if (message == null) {
                    _logger.debug("No more messages");
                    break;
                }
            }
            
        } catch (JMSException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            ActiveMQGateway.getInstance().closeConnection();
        }
    }

    /**
     * Example usage - Asynchronous listener
     */
    public static void exampleAsyncListener() {
        ActiveMQConsumer consumer = null;
        try {
            consumer = new ActiveMQConsumer();
            
            // Start listening
            consumer.startListening();
            
            _logger.debug("Listening for messages. Press Ctrl+C to stop...");
            
            // Keep the application running
            Thread.sleep(60000); // Listen for 1 minute
            
        } catch (JMSException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            ActiveMQGateway.getInstance().closeConnection();
        }
    }

    /**
     * Main method - runs async listener example
     */
    public static void main(String[] args) {
        _logger.debug("Starting ActiveMQ Consumer (Async mode)...");
        exampleAsyncListener();
        
        // To run sync mode instead, uncomment:
        // exampleSyncReceive();
    }
}
