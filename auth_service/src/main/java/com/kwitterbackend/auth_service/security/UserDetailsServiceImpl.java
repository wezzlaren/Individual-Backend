package com.kwitterbackend.auth_service.security;

import com.kwitterbackend.user_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("retrieving user");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username can't be empty");
        }
        User user = restTemplate.getForObject("http://user-service/UserController/getUser?username=" + username, User.class);
        return user;
    }
}
