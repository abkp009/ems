package com.ems;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ems")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EmsApplication implements CommandLineRunner {

	@Value("${deployment-level}")
	private String deploymentLevel;

	public static void main(String[] args) {
		SpringApplication.run(EmsApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("@@@@@@@ " + deploymentLevel + " @@@@@@ ");
		Arrays.asList(args).forEach(System.out::println);
	}
}
