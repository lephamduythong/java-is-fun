package com.example.springboottemplate.repository.debug;

public class Stopwatch {
    private long startTime;

    public Stopwatch() {
        this.startTime = System.currentTimeMillis();
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

    public long elapsed() {
        return System.currentTimeMillis() - this.startTime;
    }
}
