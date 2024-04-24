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

import com.thonglee.demo.entity.Country;
import com.thonglee.demo.entity.Product;
import com.thonglee.demo.entity.ProductCategory;
import com.thonglee.demo.entity.ProductUUID;
import com.thonglee.demo.entity.State;

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
		disableNotAllowedMethod(Product.class, config, notAllowedMethods);
		disableNotAllowedMethod(ProductCategory.class, config, notAllowedMethods);
		disableNotAllowedMethod(ProductUUID.class, config, notAllowedMethods);
		disableNotAllowedMethod(Country.class, config, notAllowedMethods);
		disableNotAllowedMethod(State.class, config, notAllowedMethods);
		
		exposeIds(config);
	}

	private void disableNotAllowedMethod(Class theClass,RepositoryRestConfiguration config, HttpMethod[] notAllowedMethods) {
		config.getExposureConfiguration()
		.forDomainType(theClass)
		.withItemExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods))
		.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(notAllowedMethods));	
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
