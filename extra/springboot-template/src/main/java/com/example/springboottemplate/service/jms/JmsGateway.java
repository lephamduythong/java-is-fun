package com.example.springboottemplate.service.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Singleton gateway class for JMS connection with JNDI lookup
 * Ensures only one connection is created and shared across the application
 * Uses JNDI to lookup ConnectionFactory and Queue from WildFly/JBoss
 */
public class JmsGateway {

    // Singleton instance
    private static volatile JmsGateway instance;
    
    private static final String QUEUE_JNDI = "java:/jms/queue/MyTestQueue";
    private static final String CONNECTION_FACTORY_JNDI = "java:/ConnectionFactory";
    
    private final ConnectionFactory connectionFactory;
    private final Queue queue;
    private Connection connection;

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");

    /**
     * Private constructor to prevent external instantiation
     */
    private JmsGateway() {
        _logger.debug("Starting JmsGateway initialization");

        try {
            InitialContext ctx = new InitialContext();
            
            // Lookup ConnectionFactory from JNDI
            this.connectionFactory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_JNDI);
            _logger.debug("ConnectionFactory lookup successful: " + CONNECTION_FACTORY_JNDI);
            
            // Lookup Queue from JNDI
            this.queue = (Queue) ctx.lookup(QUEUE_JNDI);
            _logger.debug("Queue lookup successful: " + QUEUE_JNDI);
            
            ctx.close();
            
            // Establish initial connection
            getConnection();
            
        } catch (NamingException e) {
            _logger.error("JNDI lookup failed: " + e.getMessage(), e);
            throw new RuntimeException("Failed to initialize JmsGateway", e);
        } catch (JMSException e) {
            _logger.error("Initial connection failed: " + e.getMessage(), e);
            throw new RuntimeException("Failed to establish JMS connection", e);
        }

        _logger.debug("JmsGateway initialization complete");
    }

    /**
     * Get singleton instance (thread-safe double-checked locking)
     */
    public static JmsGateway getInstance() {
        if (instance == null) {
            synchronized (JmsGateway.class) {
                if (instance == null) {
                    instance = new JmsGateway();
                }
            }
        }
        return instance;
    }

    /**
     * Get or create Connection (singleton connection)
     */
    public synchronized Connection getConnection() throws JMSException {
        if (connection == null) {
            connection = connectionFactory.createConnection();
            connection.start();
            _logger.debug("JMS connection established");
        }
        return connection;
    }
    
    /**
     * Get the Queue instance
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * Close connection
     */
    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                _logger.debug("JMS connection closed");
            } catch (JMSException e) {
                _logger.error("Error closing connection: " + e.getMessage(), e);
            }
        }
    }
    
    /**
     * Check if connection is active
     */
    public synchronized boolean isConnected() {
        try {
            if (connection != null) {
                // Try to create a session to verify connection is alive
                connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE).close();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Shutdown gateway
     */
    public synchronized void shutdown() {
        closeConnection();
        _logger.debug("JmsGateway shutdown complete");
    }
}
