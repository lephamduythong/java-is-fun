package com.thonglee.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.thonglee.demo.entity.Product;
import com.thonglee.demo.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] notAllowedMethods = { HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };
		
		config.getExposureConfiguration()
			.forDomainType(Product.class)
			.withItemExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods))
			.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods));	
		
		config.getExposureConfiguration()
			.forDomainType(ProductCategory.class)
			.withItemExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods))
			.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods));	
		
		config.exposeIdsFor(Product.class);
		config.exposeIdsFor(ProductCategory.class);
	}
}
