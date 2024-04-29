package com.turtledoctor.kgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KguApplication {

	public static void main(String[] args) {
		SpringApplication.run(KguApplication.class, args);
	}

}
