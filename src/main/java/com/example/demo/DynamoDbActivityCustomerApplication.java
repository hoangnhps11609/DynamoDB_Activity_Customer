package com.example.demo;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamoDbActivityCustomerApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(DynamoDbActivityCustomerApplication.class, args);
	}

}
