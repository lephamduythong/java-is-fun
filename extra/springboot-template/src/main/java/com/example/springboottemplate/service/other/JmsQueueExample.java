package com.example.springboottemplate.service.other;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JmsQueueExample {
    
    private static final String QUEUE_JNDI = "java:/jms/queue/MyTestQueue";
    private static final String CONNECTION_FACTORY_JNDI = "java:/ConnectionFactory";
    
    /**
     * Send a message to WildFly JMS Queue
     * @param messageText The message to send
     * @return Success message
     * @throws Exception if an error occurs
     */
    public String sendMessage(String messageText) throws Exception {
        Connection connection = null;
        Session session = null;
        
        try {
            // Get Initial Context for JNDI lookup
            Context context = getInitialContext();
            
            // Lookup ConnectionFactory and Queue
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_JNDI);
            Queue queue = (Queue) context.lookup(QUEUE_JNDI);
            
            // Create connection and session
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Create message producer
            MessageProducer producer = session.createProducer(queue);
            
            // Create and send text message
            TextMessage message = session.createTextMessage(messageText);
            producer.send(message);
            
            return "Message sent successfully to MyTestQueue: " + messageText;
            
        } finally {
            // Clean up resources
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Get Initial Context for JNDI lookups
     * This is configured for local WildFly instance
     */
    private Context getInitialContext() throws Exception {
        Properties properties = new Properties();
        // For local WildFly, we can use the default InitialContext
        // If remote, you would need to configure:
        // properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        // properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        return new InitialContext(properties);
    }
}
