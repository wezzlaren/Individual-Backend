package com.kwitterbackend.user_service.services;

import com.kwitterbackend.user_service.AuthUtil;
import com.kwitterbackend.user_service.dto.RegisterDTO;
import com.kwitterbackend.user_service.dto.UsernameDTO;
import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kwitterbackend.user_service.security.UserRole.USER;

@Service
public class UserService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<User> allusers (){
        return userRepository.findAll();
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public String registerUser(RegisterDTO user){

        User newUser = new User(user.getUsername(),new AuthUtil().encode(user.getPassword()),user.getEmail(),user.getFirstName(),user.getLastName(),
                true,true,true,true, USER.getGrantedAuthorities());
        userRepository.save(newUser);
        return "User registered";
    }

    public String changeUsername(User user, String newUsername){
        user.setUsername(newUsername);
        userRepository.save(user);
        System.out.println("username is now " + newUsername);
        return "Username updated";
    }

}
