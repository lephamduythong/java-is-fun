package com.thonglee.demo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.type.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.thonglee.demo.entity.Product;
import com.thonglee.demo.entity.ProductCategory;
import com.thonglee.demo.entity.ProductUUID;

import jakarta.persistence.EntityManager;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	@Autowired
	private EntityManager _entityManager;
	
//	public MyDataRestConfig(EntityManager entityManager) {
//		_entityManager = entityManager;
//	}
	
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
		
		config.getExposureConfiguration()
		.forDomainType(ProductUUID.class)
		.withItemExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods))
		.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods));	
		
//		config.exposeIdsFor(Product.class);
//		config.exposeIdsFor(ProductCategory.class);
//		config.exposeIdsFor(ProductUUID.class);
		exposeIds(config);
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		var entities = _entityManager.getMetamodel().getEntities();
		
		System.out.println("---------LIST OF ENTITIES-----------");
		for (var entity : entities) {
			System.out.println(entity.getName());
		}
		
		List<Class> entityClasses = new ArrayList<>();
		for (var tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		var domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}
}
