package com.thonglee.demo.service;

import java.util.List;

import com.thonglee.demo.entity.Product;

public interface ProductService {
	// Save operation 
    Product saveProduct(Product department); 
  
    // Read operation 
    List<Product> fetchProductList(); 
  
    // Update operation 
    Product updateProduct(Product department, Long departmentId); 
  
    // Delete operation 
    void deleteProductById(Long departmentId); 
}
