package com.example.springboottemplate.service.other;

public class GetSystemVarExample {
    
    public static void main(String[] args) {
        // Lấy System Properties
        System.out.println("=== SYSTEM PROPERTIES ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Home: " + System.getProperty("java.home"));
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("User Name: " + System.getProperty("user.name"));
        System.out.println("User Home: " + System.getProperty("user.home"));
        System.out.println("Current Directory: " + System.getProperty("user.dir"));
        
        // Lấy Environment Variables
        System.out.println("\n=== ENVIRONMENT VARIABLES ===");
        System.out.println("PATH: " + System.getenv("PATH"));
        System.out.println("JAVA_HOME: " + System.getenv("JAVA_HOME"));
        System.out.println("ChocolateyInstall: " + System.getenv("ChocolateyInstall"));
        
        // Lấy custom environment variable (nếu có)
        String customVar = System.getenv("CUSTOM_VAR");
        System.out.println("CUSTOM_VAR: " + (customVar != null ? customVar : "Not set"));
    }
}
