package com.example.netty.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.io.File;
import java.security.cert.CertificateException;

public class SslConfig {
    
    private static SslConfig instance;
    private SslContext sslContext;
    private final boolean sslEnabled;
    
    private SslConfig() {
        AppConfig appConfig = AppConfig.getInstance();
        this.sslEnabled = appConfig.isSslEnabled();
        
        if (sslEnabled) {
            initializeSslContext();
        }
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
            AppConfig appConfig = AppConfig.getInstance();
            String certPath = appConfig.getSslCertPath();
            String keyPath = appConfig.getSslKeyPath();
            
            if (certPath != null && keyPath != null) {
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
