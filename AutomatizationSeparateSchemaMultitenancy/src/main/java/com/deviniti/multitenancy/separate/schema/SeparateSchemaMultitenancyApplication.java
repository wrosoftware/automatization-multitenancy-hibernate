package com.deviniti.multitenancy.separate.schema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="com.deviniti.multitenancy.separate.schema")
@EntityScan(basePackages="com.deviniti.multitenancy.separate.schema")
@EnableJpaRepositories(basePackages= "com.deviniti.multitenancy.separate.schema")
public class SeparateSchemaMultitenancyApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SeparateSchemaMultitenancyApplication.class, args);
	}

}
