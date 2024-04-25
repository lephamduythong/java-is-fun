// Java Program to Demonstrate productController File 
  
// Importing package module to code fragment 
package com.thonglee.demo.controller; 
  
import java.net.http.HttpHeaders;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mariadb.jdbc.MariaDbDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thonglee.demo.entity.Product;
import com.thonglee.demo.entity.Product2;
import com.thonglee.demo.service.ProductService;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid; 

@RestController
@RequestMapping("/my-api")
public class ProductController { 

    @Autowired private ProductService productService; 
    
    @GetMapping("/products1") 
    public List<Product> fetchproductList() 
    { 
        return productService.fetchProductList(); 
    } 
    
    @GetMapping("/products2") 
    public ResponseEntity<List<Product2>> fetchproductList2(HttpServletResponse httpServletResponse) throws SQLException 
    { 
    	DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
    	var connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/full-stack-ecommerce", "ecommerceapp", "ecommerceapp");
    	if (connection != null) {
    		System.out.println("OK Connection");
    	}
    	var queryRunner = new QueryRunner();
    	var resultHandler = new BeanListHandler<>(Product2.class);
    	var productList = queryRunner.query(connection, "SELECT * FROM product", resultHandler);
    	for (var product : productList) {
			System.out.println(product.getName());
		}
    	
    	var rh = new org.springframework.http.HttpHeaders();
    	rh.set("ThongHeader", "In god we trust!");
    	
    	return ResponseEntity
    			.ok().headers(rh)
    			.body(productList);
    } 
    
    @GetMapping("/products3") 
    public ResponseEntity<List<Product2>> fetchproductList3(HttpServletResponse httpServletResponse) throws SQLException 
    { 
    	DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
    	var connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/full-stack-ecommerce", "ecommerceapp", "ecommerceapp");
    	if (connection != null) {
    		System.out.println("OK Connection");
    	}
    	var queryRunner = new QueryRunner();
    	var resultHandler = new BeanListHandler<>(Product2.class);
    	var productList = queryRunner.query(connection, "SELECT * FROM product", resultHandler);
    	for (var product : productList) {
    		System.out.println(product.getName());
    	}
    	
    	var rh = new org.springframework.http.HttpHeaders();
    	rh.set("ThongHeader", "In god we trust!");
    	
    	return ResponseEntity
    			.ok().headers(rh)
    			.body(productList);
    } 
    
    @PostMapping("/products2") 
    public Product saveproduct(@Valid @RequestBody Product product) 
    { 
        return productService.saveProduct(product); 
    } 
 
//    // Update operation 
//    @PutMapping("/products/{id}") 
//    public product 
//    updateproduct(@RequestBody product product, 
//                     @PathVariable("id") Long productId) 
//    { 
//        return productService.updateproduct( 
//            product, productId); 
//    } 
//  
//    // Delete operation 
//    @DeleteMapping("/products/{id}") 
//    public String deleteproductById(@PathVariable("id") 
//                                       Long productId) 
//    { 
//        productService.deleteproductById( 
//            productId); 
//  
//        return "Deleted Successfully"; 
//    } 
}