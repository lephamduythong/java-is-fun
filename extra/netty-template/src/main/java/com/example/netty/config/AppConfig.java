package com.example.netty.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    
    private static final String CONFIG_FILE = "config/config.properties";
    private static AppConfig instance;
    private final Properties properties;
    
    private AppConfig() {
        this.properties = new Properties();
        loadConfig();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            System.out.println("Configuration loaded from " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("Configuration file not found, using default values");
        }
    }
    
    public boolean isUseSqliteSession() {
        return Boolean.parseBoolean(properties.getProperty("session.storage.sqlite", "false"));
    }
    
    public String getSqliteDatabasePath() {
        return properties.getProperty("session.storage.sqlite.path", "sessions.db");
    }
    
    public int getSessionCleanupIntervalHours() {
        return Integer.parseInt(properties.getProperty("session.cleanup.interval.hours", "1"));
    }
    
    public boolean isSslEnabled() {
        return Boolean.parseBoolean(properties.getProperty("ssl.enabled", "false"));
    }
    
    public String getSslCertPath() {
        return properties.getProperty("ssl.cert.path");
    }
    
    public String getSslKeyPath() {
        return properties.getProperty("ssl.key.path");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
