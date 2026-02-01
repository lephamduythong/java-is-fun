package com.example.springboottemplate.service.activemq;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Singleton gateway class for ActiveMQ Classic connection
 * Ensures only one connection is created and shared across the application
 * Uses ActiveMQ's built-in failover transport for automatic reconnection
 */
public class ActiveMQGateway {

    // Singleton instance
    private static volatile ActiveMQGateway instance;
    
    private final ConnectionFactory connectionFactory;
    private Connection connection;

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");

    /**
     * Private constructor to prevent external instantiation
     */
    private ActiveMQGateway() {
        _logger.debug("Starting ActiveMQGateway initialization");

        ActiveMQConfig config = ActiveMQConfig.getInstance();
        
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        
        // Build failover URL with auto-reconnect configuration
        String brokerUrl = config.getBrokerUrl();
        String failoverUrl = buildFailoverUrl(brokerUrl, config.getReconnectIntervalSeconds());
        
        factory.setBrokerURL(failoverUrl);
        factory.setUserName(config.getUsername());
        factory.setPassword(config.getPassword());
        factory.setTrustAllPackages(config.isTrustAllPackages());
        
        this.connectionFactory = factory;

        // Establish initial connection
        try {
            getConnection();
        } catch (JMSException e) {
            _logger.error("Initial connection failed, will auto-reconnect: " + e.getMessage());
        }

        _logger.debug("ActiveMQGateway initialization complete");
    }
    
    /**
     * Build failover URL with reconnect configuration
     */
    private String buildFailoverUrl(String brokerUrl, long reconnectIntervalSeconds) {
        // If already a failover URL, return as is
        if (brokerUrl.startsWith("failover:")) {
            return brokerUrl;
        }
        
        // Build failover URL with reconnect options
        long reconnectDelayMs = reconnectIntervalSeconds * 1000;

        _logger.debug("Configuring failover with reconnect interval (ms): " + reconnectDelayMs);
        
        String failoverUrl = String.format(
            "failover:(%s)?maxReconnectAttempts=3&initialReconnectDelay=5000&useExponentialBackOff=false",
            brokerUrl,
            reconnectDelayMs
        );
        
        _logger.debug("Using failover URL with 5 retry attempts: " + failoverUrl);

        return failoverUrl;
    }

    /**
     * Get singleton instance (thread-safe double-checked locking)
     */
    public static ActiveMQGateway getInstance() {
        if (instance == null) {
            synchronized (ActiveMQGateway.class) {
                if (instance == null) {
                    instance = new ActiveMQGateway();
                }
            }
        }
        return instance;
    }

    /**
     * Get or create Connection (singleton connection)
     * ActiveMQ failover transport handles reconnection automatically
     */
    public synchronized Connection getConnection() throws JMSException {
        if (connection == null || ((org.apache.activemq.ActiveMQConnection) connection).isClosed()
        ) {
            connection = connectionFactory.createConnection();
            connection.start();
            _logger.debug("ActiveMQ connection established");
        }
        if (connection != null 
            && ((org.apache.activemq.ActiveMQConnection) connection).isTransportFailed()
        ) {
            closeConnection();
            connection = connectionFactory.createConnection();
            connection.start();
            _logger.debug("ActiveMQ connection re-established after failure");
        }
        return connection;
    }

    /**
     * Close connection
     */
    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                _logger.debug("ActiveMQ connection closed");
            } catch (JMSException e) {
                _logger.error("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if connection is active
     */
    public synchronized boolean isConnected() {
        try {
            return connection != null && !((org.apache.activemq.ActiveMQConnection) connection).isClosed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Shutdown gateway
     */
    public synchronized void shutdown() {
        closeConnection();
        _logger.debug("ActiveMQGateway shutdown complete");
    }
}