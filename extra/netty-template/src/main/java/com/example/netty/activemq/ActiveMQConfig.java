package com.example.netty.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Singleton configuration class for ActiveMQ Classic connection
 * Ensures only one connection is created and shared across the application
 * Includes auto-reconnect mechanism
 */
public class ActiveMQConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final long RECONNECT_INTERVAL_SECONDS = 10;

    // Singleton instance
    private static volatile ActiveMQConfig instance;
    
    private final ConnectionFactory connectionFactory;
    private Connection connection;
    private final ScheduledExecutorService reconnectScheduler;
    private volatile boolean autoReconnectEnabled = false;

    /**
     * Private constructor to prevent external instantiation
     */
    private ActiveMQConfig() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(BROKER_URL);
        factory.setUserName(USERNAME);
        factory.setPassword(PASSWORD);
        
        // Optional: Configure connection pool and other settings
        factory.setTrustAllPackages(true);
        this.connectionFactory = factory;
        
        // Initialize reconnect scheduler
        this.reconnectScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "ActiveMQ-Reconnect-Thread");
            thread.setDaemon(true);
            return thread;
        });
    }

    /**
     * Get singleton instance (thread-safe double-checked locking)
     */
    public static ActiveMQConfig getInstance() {
        if (instance == null) {
            synchronized (ActiveMQConfig.class) {
                if (instance == null) {
                    instance = new ActiveMQConfig();
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
    public Connection getConnection() throws JMSException {
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
    public boolean isConnected() {
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
        }, RECONNECT_INTERVAL_SECONDS, RECONNECT_INTERVAL_SECONDS, TimeUnit.SECONDS);
        
        System.out.println("Auto-reconnect enabled (checking every " + RECONNECT_INTERVAL_SECONDS + " seconds)");
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
