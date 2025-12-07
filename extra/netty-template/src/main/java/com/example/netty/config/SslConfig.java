package com.example.netty.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.Properties;

public class SslConfig {
    
    private static SslConfig instance;
    private SslContext sslContext;
    private final boolean sslEnabled;
    private final String certPath;
    private final String keyPath;
    
    private SslConfig() {
        Properties properties = loadProperties();
        this.sslEnabled = Boolean.parseBoolean(properties.getProperty("ssl.enabled", "false"));
        this.certPath = properties.getProperty("ssl.cert.path", "").trim();
        this.keyPath = properties.getProperty("ssl.key.path", "").trim();
        
        if (sslEnabled) {
            initializeSslContext();
        }
    }
    
    private Properties loadProperties() {
        Properties properties = new Properties();
        String configPath = "config/ssl.properties";
        
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
            System.out.println("Loaded SSL configuration from " + configPath);
        } catch (IOException e) {
            System.err.println("Could not load ssl.properties, using defaults: " + e.getMessage());
        }
        
        return properties;
    }
    
    public static SslConfig getInstance() {
        if (instance == null) {
            synchronized (SslConfig.class) {
                if (instance == null) {
                    instance = new SslConfig();
                }
            }
        }
        return instance;
    }
    
    private void initializeSslContext() {
        try {
            if (certPath != null && !certPath.isEmpty() && keyPath != null && !keyPath.isEmpty()) {
                // Use provided certificate and key
                File certFile = new File(certPath);
                File keyFile = new File(keyPath);
                
                if (certFile.exists() && keyFile.exists()) {
                    sslContext = SslContextBuilder.forServer(certFile, keyFile).build();
                    System.out.println("SSL enabled with custom certificate");
                } else {
                    System.out.println("Certificate or key file not found, falling back to self-signed certificate");
                    useSelfSignedCertificate();
                }
            } else {
                // Use self-signed certificate for development
                useSelfSignedCertificate();
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize SSL context: " + e.getMessage());
            System.err.println("HTTPS server will not be available. Please check your SSL configuration.");
            e.printStackTrace();
            sslContext = null;
        }
    }
    
    private void useSelfSignedCertificate() throws CertificateException, SSLException {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        System.out.println("SSL enabled with self-signed certificate (for development only)");
        System.out.println("Certificate: " + ssc.certificate().getAbsolutePath());
        System.out.println("Private Key: " + ssc.privateKey().getAbsolutePath());
    }
    
    public boolean isSslEnabled() {
        return sslEnabled;
    }
    
    public SslContext getSslContext() {
        return sslContext;
    }
}
