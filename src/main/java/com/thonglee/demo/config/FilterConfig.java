package com.thonglee.demo.config;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thonglee.demo.filter.TransactionFilter;
import com.thonglee.demo.filter.UserFilter;

@Configuration
public class FilterConfig  {
	
	@Bean // UserFile: do first
	FilterRegistrationBean<UserFilter> userFilter() {
		var bean = new FilterRegistrationBean<UserFilter>();
		bean.setFilter(new UserFilter());
		bean.addUrlPatterns("/my-api/products3/*");
		return bean;
	}
	
	@Bean // TransactionFilter: do second
	FilterRegistrationBean<TransactionFilter> transactionFilter() {
		var bean = new FilterRegistrationBean<TransactionFilter>();
		bean.setFilter(new TransactionFilter());
		bean.addUrlPatterns("/my-api/products3/*");
		return bean;
	}

}
