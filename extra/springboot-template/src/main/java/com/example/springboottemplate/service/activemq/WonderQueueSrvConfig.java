package com.example.springboottemplate.service.activemq;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class for ActiveMQ settings
 * Reads from config/activemq.properties
 */
public class WonderQueueSrvConfig {
    
    private static WonderQueueSrvConfig instance;
    
    private final String brokerUrl;
    private final String username;
    private final String password;
    private final long reconnectIntervalSeconds;
    private final boolean trustAllPackages;
    
    private WonderQueueSrvConfig() {
        Properties properties = loadProperties();
        this.brokerUrl = properties.getProperty("activemq.broker.url", "tcp://localhost:61616");
        this.username = properties.getProperty("activemq.username", "admin");
        this.password = properties.getProperty("activemq.password", "admin");
        this.reconnectIntervalSeconds = Long.parseLong(properties.getProperty("activemq.reconnect.interval.seconds", "10"));
        this.trustAllPackages = Boolean.parseBoolean(properties.getProperty("activemq.trust.all.packages", "true"));
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
