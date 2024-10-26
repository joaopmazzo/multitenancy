package br.com.joaopmazzo.multitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MultitenancyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultitenancyApplication.class, args);
	}

}
