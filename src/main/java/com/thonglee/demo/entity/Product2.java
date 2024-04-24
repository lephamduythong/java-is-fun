package com.thonglee.demo.entity;

import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
public class Product2 {
	private Long id;
	
	private String sku;
	
	private String name;
	
	private String description;
	
	private BigDecimal unnitPrice;
	
	private String imageUrl;
	
	private boolean active;
	
	private int unitsInStock;
	
	private Date dateCreated;

	private Date lastUpdated;
}
