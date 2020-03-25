package com.kwitterbackend.eureka_registerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaRegisterserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRegisterserviceApplication.class, args);
    }

}
