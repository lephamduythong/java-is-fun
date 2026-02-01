package com.example.springboottemplate.service.jms;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JMS Message Consumer using JNDI lookup
 * Receives messages from a queue configured via JNDI
 */
public class JmsConsumer {
    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageConsumer jmsConsumer;
    private Queue queue;

    /**
     * Initialize consumer using JmsGateway
     */
    public JmsConsumer() throws JMSException {
        connection = JmsGateway.getInstance().getConnection();
        // false: no transactions
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Get queue from JNDI lookup via JmsGateway
        queue = JmsGateway.getInstance().getQueue();
        
        jmsConsumer = session.createConsumer(queue);
        
        _logger.debug("JmsConsumer initialized for queue: " + queue.getQueueName());
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
            _logger.debug("Cute Message received: " + text);
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
                        _logger.info("Async message received: " + text);
                        
                        // Check for custom properties
                        if (message.propertyExists("priority")) {
                            String priority = message.getStringProperty("priority");
                            _logger.info("  Priority: " + priority);
                        }
                    }
                } catch (JMSException e) {
                    _logger.error("Error processing message: " + e.getMessage(), e);
                }
            }
        });
        _logger.debug("Started listening for messages...");
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
            _logger.debug("JmsConsumer closed");
        } catch (JMSException e) {
            _logger.error("Error closing consumer: " + e.getMessage(), e);
        }
    }

    /**
     * Example usage - Synchronous receive
     */
    public static void exampleSyncReceive() {
        JmsConsumer consumer = null;
        try {
            consumer = new JmsConsumer();
            
            _logger.info("Waiting for messages (5 second timeout)...");
            
            // Receive up to 5 messages with 5 second timeout
            for (int i = 0; i < 5; i++) {
                String message = consumer.receiveMessage(5000);
                if (message == null) {
                    _logger.info("No more messages");
                    break;
                }
            }
            
        } catch (JMSException e) {
            _logger.error("Error: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            JmsGateway.getInstance().closeConnection();
        }
    }

    /**
     * Example usage - Asynchronous listener
     */
    public static void exampleAsyncListener() {
        JmsConsumer consumer = null;
        try {
            consumer = new JmsConsumer();
            
            // Start listening
            consumer.startListening();
            
            _logger.info("Listening for messages. Press Ctrl+C to stop...");
            
            // Keep the application running
            Thread.sleep(60000); // Listen for 1 minute
            
        } catch (JMSException | InterruptedException e) {
            _logger.error("Error: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                consumer.close();
            }
            JmsGateway.getInstance().closeConnection();
        }
    }

    /**
     * Main method - runs async listener example
     */
    public static void main(String[] args) {
        _logger.info("Starting JMS Consumer (Async mode)...");
        exampleAsyncListener();
        
        // To run sync mode instead, uncomment:
        // exampleSyncReceive();
    }
}
