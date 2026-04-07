package com.jpinto.orchestator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class OrchestatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestatorApplication.class, args);
	}

}
