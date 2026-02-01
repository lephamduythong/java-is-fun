package com.example.springboottemplate.service.activemq;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveMQ Message Producer
 * Sends messages to a queue or topic
 */
public class WonderQueueSrvProducer {
    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageProducer jmsProducer;
    private Destination destination;

    /**
     * Initialize producer with custom destination
     * @param destinationName Name of queue or topic
     * @param isTopic true for topic, false for queue
     */
    public WonderQueueSrvProducer(String destinationName, boolean isTopic) throws JMSException {
        connection = WonderQueueSrv.getInstance().getConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        if (isTopic) {
            destination = session.createTopic(destinationName);
        } else {
            destination = session.createQueue(destinationName);
        }
        
        jmsProducer = session.createProducer(destination);
        jmsProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        _logger.debug("Producer initialized for: " + destinationName);
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
            _logger.debug("Producer closed");
        } catch (JMSException e) {
            System.err.println("Error closing producer: " + e.getMessage());
        }
    }

    /**
     * Example usage
     */
    public static void main(String[] args) {
        WonderQueueSrvProducer producer = null;
        try {
            // Create producer
            producer = new WonderQueueSrvProducer("thong.topic.request", true);
            
            // Send single message
            producer.sendMessage("Hello from ActiveMQ!");
            
            // Send message with property
            producer.sendMessage("Important message", "priority", "high");
            
            // Send multiple messages
            String[] messages = {
                "Message 1",
                "Message 2",
                "Message 3"
            };
            producer.sendMessages(messages);
            
            _logger.debug("All messages sent successfully!");
            
        } catch (JMSException e) {
            _logger.error("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (producer != null) {
                producer.close();
            }
        }
    }
}
