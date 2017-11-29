package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class OkbitpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkbitpayApplication.class, args);
	}
}
