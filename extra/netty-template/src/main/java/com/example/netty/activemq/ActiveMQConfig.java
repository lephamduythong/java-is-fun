package com.example.netty.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Configuration class for ActiveMQ Classic connection
 */
public class ActiveMQConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private static ConnectionFactory connectionFactory;
    private static Connection connection;

    /**
     * Get or create ConnectionFactory
     */
    public static ConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
            factory.setBrokerURL(BROKER_URL);
            factory.setUserName(USERNAME);
            factory.setPassword(PASSWORD);
            
            // Optional: Configure connection pool and other settings
            factory.setTrustAllPackages(true);
            connectionFactory = factory;
        }
        return connectionFactory;
    }

    /**
     * Get or create Connection
     */
    public static Connection getConnection() throws JMSException {
        if (connection == null || ((org.apache.activemq.ActiveMQConnection) connection).isClosed()) {
            connection = getConnectionFactory().createConnection();
            connection.start();
        }
        return connection;
    }

    /**
     * Close connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("ActiveMQ connection closed");
            } catch (JMSException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
