package com.thonglee.demo;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws SQLException, IOException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
