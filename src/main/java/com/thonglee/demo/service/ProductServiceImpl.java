package com.thonglee.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thonglee.demo.dao.ProductRepository;
import com.thonglee.demo.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
    private ProductRepository departmentRepository; 

	@Override
	public Product saveProduct(Product department) {
		return departmentRepository.save(department);
	}

	@Override
	public List<Product> fetchProductList() {
		System.out.println("GET");
		return (List<Product>) departmentRepository.findAll(); 
	}

	@Override
	public Product updateProduct(Product department, Long departmentId) {
		System.out.println("UPDATE");
		return null;
	}

	@Override
	public void deleteProductById(Long departmentId) {
		System.out.println("DELETE");
	}
	
}
