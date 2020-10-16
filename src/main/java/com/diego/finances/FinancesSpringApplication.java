package com.diego.finances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FinancesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancesSpringApplication.class, args);
	}

}
