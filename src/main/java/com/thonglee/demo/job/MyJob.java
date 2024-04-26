package com.thonglee.demo.job;

import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
//@DisallowConcurrentExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Job logic goes here
    	var rnd = new Random();
    	System.out.println(rnd.nextInt(0, 100));
    	try {
			Thread.sleep(1000000);
			System.out.println("END");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
}