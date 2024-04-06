package com.redmath.institution;

import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableIgniteRepositories(basePackages = {"com.redmath.institution.*"})
public class InstitutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstitutionApplication.class, args);
	}

}
