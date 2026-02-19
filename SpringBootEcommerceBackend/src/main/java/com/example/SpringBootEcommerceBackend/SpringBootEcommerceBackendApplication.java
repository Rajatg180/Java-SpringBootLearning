package com.example.SpringBootEcommerceBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // this annotation is used to enable transaction management in Spring Boot. It allows you to use the @Transactional annotation on your service methods to manage transactions automatically.
public class SpringBootEcommerceBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEcommerceBackendApplication.class, args);
	}

}
