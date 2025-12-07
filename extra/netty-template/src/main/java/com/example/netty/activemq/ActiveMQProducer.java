package com.example.netty.activemq;

import javax.jms.*;

/**
 * ActiveMQ Message Producer
 * Sends messages to a queue or topic
 */
public class ActiveMQProducer {
    private static final String DEFAULT_QUEUE = "sample.queue";
    
    private Connection connection;
    private Session session;
    private javax.jms.MessageProducer jmsProducer;
    private Destination destination;

    /**
     * Initialize producer with default queue
     */
    public ActiveMQProducer() throws JMSException {
        this(DEFAULT_QUEUE, false);
    }

    /**
     * Initialize producer with custom destination
     * @param destinationName Name of queue or topic
     * @param isTopic true for topic, false for queue
     */
    public ActiveMQProducer(String destinationName, boolean isTopic) throws JMSException {
        connection = ActiveMQGateway.getInstance().getConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        if (isTopic) {
            destination = session.createTopic(destinationName);
        } else {
            destination = session.createQueue(destinationName);
        }
        
        jmsProducer = session.createProducer(destination);
        jmsProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        System.out.println("Producer initialized for: " + destinationName);
    }

    /**
     * Send a text message
     */
    public void sendMessage(String text) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        jmsProducer.send(message);
        System.out.println("Message sent: " + text);
    }

    /**
     * Send a text message with properties
     */
    public void sendMessage(String text, String propertyKey, String propertyValue) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        message.setStringProperty(propertyKey, propertyValue);
        jmsProducer.send(message);
        System.out.println("Message sent with property: " + text);
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
            System.out.println("Producer closed");
        } catch (JMSException e) {
            System.err.println("Error closing producer: " + e.getMessage());
        }
    }

    /**
     * Example usage
     */
    public static void main(String[] args) {
        ActiveMQProducer producer = null;
        try {
            // Create producer
            producer = new ActiveMQProducer();
            
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
            
            System.out.println("All messages sent successfully!");
            
        } catch (JMSException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (producer != null) {
                producer.close();
            }
            ActiveMQGateway.getInstance().closeConnection();
        }
    }
}
