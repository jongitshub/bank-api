package com.bank_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.bank_api.model")
@EnableJpaRepositories(basePackages = "com.bank_api.repository")
public class BankApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }
}
