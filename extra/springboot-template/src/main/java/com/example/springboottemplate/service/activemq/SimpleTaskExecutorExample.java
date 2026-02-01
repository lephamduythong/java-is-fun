package com.example.springboottemplate.service.activemq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleTaskExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService timeoutChecker = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(500);
                    System.out.println("Long task");
                } catch (InterruptedException e) {
                    System.out.println("Long task interrupted!");
                    Thread.currentThread().interrupt(); // Restore interrupt status
                    break; // Exit the loop
                }
            }
        });

        timeoutChecker.submit(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    count++;
                    System.out.println("Checking for timeout...");
                    if (count >= 3) {
                        System.out.println("Timeout detected! Shutting down long task.");
                        executor.shutdownNow();
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println("Timeout checker interrupted!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
