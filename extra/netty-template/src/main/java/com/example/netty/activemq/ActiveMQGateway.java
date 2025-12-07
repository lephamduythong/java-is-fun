package com.example.netty.activemq;

import com.example.netty.config.ActiveMQConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Singleton gateway class for ActiveMQ Classic connection
 * Ensures only one connection is created and shared across the application
 * Includes auto-reconnect mechanism
 */
public class ActiveMQGateway {
    private final long reconnectIntervalSeconds;

    // Singleton instance
    private static volatile ActiveMQGateway instance;
    
    private final ConnectionFactory connectionFactory;
    private Connection connection;
    private final ScheduledExecutorService reconnectScheduler;
    private volatile boolean autoReconnectEnabled = false;

    /**
     * Private constructor to prevent external instantiation
     */
    private ActiveMQGateway() {
        ActiveMQConfig config = ActiveMQConfig.getInstance();
        this.reconnectIntervalSeconds = config.getReconnectIntervalSeconds();
        
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(config.getBrokerUrl());
        factory.setUserName(config.getUsername());
        factory.setPassword(config.getPassword());
        
        // Optional: Configure connection pool and other settings
        factory.setTrustAllPackages(config.isTrustAllPackages());
        this.connectionFactory = factory;
        
        // Initialize reconnect scheduler
        this.reconnectScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "ActiveMQ-Reconnect-Thread");
            thread.setDaemon(true);
            return thread;
        });

        // Establish initial connection
        try {
            getConnection();
        } catch (JMSException e) {
            System.err.println("Initial connection failed: " + e.getMessage());
            // Start auto-reconnect even if initial connection fails
            startAutoReconnect();
        }
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
     * Get ConnectionFactory
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Get or create Connection (singleton connection)
     */
    public synchronized Connection getConnection() throws JMSException {
        if (connection == null || ((org.apache.activemq.ActiveMQConnection) connection).isClosed()) {
            connection = connectionFactory.createConnection();
            connection.start();
            System.out.println("ActiveMQ connection established");
            
            // Enable auto-reconnect when first connection is made
            if (!autoReconnectEnabled) {
                startAutoReconnect();
            }
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
                System.out.println("ActiveMQ connection closed");
            } catch (JMSException e) {
                System.err.println("Error closing connection: " + e.getMessage());
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
     * Start auto-reconnect mechanism
     */
    private void startAutoReconnect() {
        if (autoReconnectEnabled) {
            return;
        }
        
        autoReconnectEnabled = true;
        reconnectScheduler.scheduleAtFixedRate(() -> {
            try {
                if (!isConnected()) {
                    System.out.println("Connection lost, attempting to reconnect...");
                    synchronized (this) {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (Exception e) {
                                // Ignore errors when closing dead connection
                            }
                            connection = null;
                        }
                        
                        // Try to reconnect
                        try {
                            connection = connectionFactory.createConnection();
                            connection.start();
                            System.out.println("Successfully reconnected to ActiveMQ");
                        } catch (JMSException e) {
                            System.err.println("Reconnection failed: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error in auto-reconnect: " + e.getMessage());
            }
        }, reconnectIntervalSeconds, reconnectIntervalSeconds, TimeUnit.SECONDS);
        
        System.out.println("Auto-reconnect enabled (checking every " + reconnectIntervalSeconds + " seconds)");
    }
    
    /**
     * Stop auto-reconnect and shutdown
     */
    public void shutdown() {
        autoReconnectEnabled = false;
        closeConnection();
        reconnectScheduler.shutdown();
        try {
            if (!reconnectScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                reconnectScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            reconnectScheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("ActiveMQ config shutdown complete");
    }
}
