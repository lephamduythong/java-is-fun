package com.thonglee.demo.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TransactionFilter implements Filter  {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init TransactionFilter");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("TransactionFilter: doFilter");
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy TransactionFilter");
	}
}
