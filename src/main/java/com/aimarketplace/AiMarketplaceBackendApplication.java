package com.aimarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AiMarketplaceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiMarketplaceBackendApplication.class, args);
	}

}
