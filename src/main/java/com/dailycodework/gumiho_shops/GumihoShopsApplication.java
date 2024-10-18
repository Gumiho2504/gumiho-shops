package com.dailycodework.gumiho_shops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = " com.dailycodework.gumiho_shops.model")
public class GumihoShopsApplication {

	public static void main(String[] args) {

		SpringApplication.run(GumihoShopsApplication.class, args);

	}

}
