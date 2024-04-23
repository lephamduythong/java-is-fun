// Java Program to Demonstrate productController File 
  
// Importing package module to code fragment 
package com.thonglee.demo.controller; 
  
import java.util.List; 
// Importing required classes 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.*;

import com.thonglee.demo.entity.Product;
import com.thonglee.demo.service.ProductService;

import jakarta.validation.Valid; 

// Annotation 
@RestController
  
// Class 
public class ProductController { 
  
    @Autowired private ProductService productService; 
    
    // Read operation 
    @GetMapping("/products") 
    public List<Product> fetchproductList() 
    { 
        return productService.fetchProductList(); 
    } 
    
    // Save operation 
    @PostMapping("/products") 
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