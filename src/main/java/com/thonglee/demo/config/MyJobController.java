package com.thonglee.demo.config;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class MyJobController {
	
    @Autowired
    private Scheduler _scheduler;
	
	@GetMapping("/state")
	public String status() throws SchedulerException {
//		var scheduler = _schedulerFactory.getScheduler();
		var triggerKey = new TriggerKey("myJobTrigger");
		var state = _scheduler.getTriggerState(triggerKey);
		return state.name().toString();
	}
	
	@PostMapping("/shutdown")
	public String shutdown() throws SchedulerException {
		_scheduler.shutdown();
		return "shutdown job";
	}
	
	@PostMapping("/start")
	public String start() throws SchedulerException {
		_scheduler.start();
		return "start job";
	}
	
	@PostMapping("/pause-job")
	public String pauseJob() throws SchedulerException {
		var jobKey = new JobKey("myJob", "myJobGroup");
		_scheduler.pauseJob(jobKey);
		return "pauseJob";
	}
	
}
