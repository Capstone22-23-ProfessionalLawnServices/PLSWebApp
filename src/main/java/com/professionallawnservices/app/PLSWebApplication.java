package com.professionallawnservices.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-dev.properties")
public class PLSWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PLSWebApplication.class, args);
	}

}
