package com.kwitterbackend.user_service;

import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.kwitterbackend.user_service.security.UserRole.ADMIN;
import static com.kwitterbackend.user_service.security.UserRole.USER;

@EnableEurekaClient
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuthUtil auth() { return new AuthUtil();}

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, AuthUtil auth){
        return args -> {

            User user1 = new User("wezzlaren",auth.encode("test1"),
                    true,true,true,true, USER.getGrantedAuthorities());
            User user2 = new User("admin",auth.encode("test5"),
                    true,true,true,true, ADMIN.getGrantedAuthorities());
            user1 = userRepository.save(user1);
            user2 = userRepository.save(user2);
            System.out.println("saved: " + user1.getUsername());
            System.out.println("saved: " + user2.getUsername());
        };
    }

    @Configuration
    class RestTemplateConfig {

        // Create a bean for restTemplate to call services
        @Bean
        @LoadBalanced        // Load balance between service instances running at different ports.
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }


}
