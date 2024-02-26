package com.michaelrkaplan.bakersassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.michaelrkaplan.bakersassistant")
public class BakersAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakersAssistantApplication.class, args);
	}

}
