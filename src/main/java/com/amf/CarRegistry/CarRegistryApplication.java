package com.amf.CarRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.amf.CarRegistry"})
public class CarRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRegistryApplication.class, args);
	}
}
