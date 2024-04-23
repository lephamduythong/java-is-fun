package com.thonglee.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.thonglee.demo.entity.ProductCategory;

@Repository
//@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-categories") // if default: "productCategories"
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
