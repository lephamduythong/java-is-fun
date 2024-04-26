package com.thonglee.demo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.thonglee.demo.job.MyJob;

@Configuration
public class QuartzConfig {
	@Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        var schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        schedulerFactoryBean.setTriggers(myJobTrigger().getObject());
        
        // Limit number of threads for concurrent running
        Properties quartzProperties = new Properties();     
        quartzProperties.put("org.quartz.threadPool.threadCount", "3");
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        
        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        var jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public JobDetailFactoryBean myJobDetail() {
        var jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJob.class);
        jobDetailFactory.setName("myJob");
        jobDetailFactory.setGroup("myJobGroup");
        //jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean myJobTrigger() {
        var cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(myJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression("0/2 * * * * ?"); // Run every 10 seconds
        cronTriggerFactoryBean.setName("myJobTrigger");
        cronTriggerFactoryBean.setGroup("myJobGroup");
        return cronTriggerFactoryBean;
    }
}
