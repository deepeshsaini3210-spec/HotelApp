package com.grandstay.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.grandstay.hotel")
@EnableFeignClients(basePackages = "com.grandstay.hotel.controller")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
