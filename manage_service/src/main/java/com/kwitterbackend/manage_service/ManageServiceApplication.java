package com.kwitterbackend.manage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageServiceApplication.class, args);
    }

}
