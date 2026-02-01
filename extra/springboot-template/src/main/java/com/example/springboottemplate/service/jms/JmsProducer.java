package com.example.springboottemplate.service.jms;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JMS Message Producer using JNDI lookup
 * Sends messages to a queue configured via JNDI
 */
public class JmsProducer {
    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageProducer jmsProducer;
    private Queue queue;

    /**
     * Initialize producer using JmsGateway
     */
    public JmsProducer() throws JMSException {
        connection = JmsGateway.getInstance().getConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        // Get queue from JNDI lookup via JmsGateway
        queue = JmsGateway.getInstance().getQueue();
        
        jmsProducer = session.createProducer(queue);
        jmsProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        _logger.debug("JmsProducer initialized for queue: " + queue.getQueueName());
    }

    /**
     * Send a text message
     */
    public void sendMessage(String text) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        jmsProducer.send(message);
        _logger.debug("Message sent: " + text);
    }

    /**
     * Send a text message with properties
     */
    public void sendMessage(String text, String propertyKey, String propertyValue) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        message.setStringProperty(propertyKey, propertyValue);
        jmsProducer.send(message);
        _logger.debug("Message sent with property: " + text);
    }

    /**
     * Send multiple messages
     */
    public void sendMessages(String[] messages) throws JMSException {
        for (String msg : messages) {
            sendMessage(msg);
        }
    }

    /**
     * Close producer and session
     */
    public void close() {
        try {
            if (jmsProducer != null) {
                jmsProducer.close();
            }
            if (session != null) {
                session.close();
            }
            _logger.debug("JmsProducer closed");
        } catch (JMSException e) {
            _logger.error("Error closing producer: " + e.getMessage(), e);
        }
    }

    /**
     * Example usage
     */
    public static void main(String[] args) {
        JmsProducer producer = null;
        try {
            // Create producer
            producer = new JmsProducer();
            
            // Send single message
            producer.sendMessage("Hello from JMS!");
            
            // Send message with property
            producer.sendMessage("Important message", "priority", "high");
            
            // Send multiple messages
            String[] messages = {
                "JMS Message 1",
                "JMS Message 2",
                "JMS Message 3"
            };
            producer.sendMessages(messages);
            
            _logger.debug("All messages sent successfully!");
            
        } catch (JMSException e) {
            _logger.error("Error: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (producer != null) {
                producer.close();
            }
        }
    }
}
