package com.thonglee.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.thonglee.demo.entity.Product;

@CrossOrigin("http://localhost:4200")
@Repository
//@RepositoryRestResource(collectionResourceRel = "product", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);	
	Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);	
}
 