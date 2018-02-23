package com.alexsnowm.grocerycomparer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GroceryComparerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryComparerApplication.class, args);
	}
}
