package com.example.springboottemplate.service.activemq;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for ActiveMQ settings
 * Reads from config/activemq.properties
 */
public class WonderQueueSrvConfig {
    
    private static WonderQueueSrvConfig instance;
    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");
    
    private final String brokerUrl;
    private final String username;
    private final String password;
    private final long reconnectIntervalSeconds;
    private final boolean trustAllPackages;
    
    private WonderQueueSrvConfig() {
        this.brokerUrl = System.getenv("ACTIVEMQ_BROKER_URL");
        this.username = System.getenv("ACTIVEMQ_USERNAME");
        this.password = System.getenv("ACTIVEMQ_PASSWORD");
        this.reconnectIntervalSeconds = 10;
        this.trustAllPackages = true;

        _logger.debug("ActiveMQ Config - Broker URL: " + brokerUrl);
        _logger.debug("ActiveMQ Config - Username: " + username);
        _logger.debug("ActiveMQ Config - Password: " + password);
        _logger.debug("ActiveMQ Config - Reconnect Interval Seconds: " + reconnectIntervalSeconds);
        _logger.debug("ActiveMQ Config - Trust All Packages: " + trustAllPackages);
    }
    
    public static WonderQueueSrvConfig getInstance() {
        if (instance == null) {
            synchronized (WonderQueueSrvConfig.class) {
                if (instance == null) {
                    instance = new WonderQueueSrvConfig();
                }
            }
        }
        return instance;
    }
    
    private Properties loadProperties() {
        Properties properties = new Properties();
        String configPath = "src/main/resources/configs/activemq.properties";
        
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
            System.out.println("Loaded ActiveMQ configuration from " + configPath);
        } catch (IOException e) {
            System.err.println("Could not load activemq.properties, using defaults: " + e.getMessage());
        }
        
        return properties;
    }
    
    public String getBrokerUrl() {
        return brokerUrl;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public long getReconnectIntervalSeconds() {
        return reconnectIntervalSeconds;
    }
    
    public boolean isTrustAllPackages() {
        return trustAllPackages;
    }
}
