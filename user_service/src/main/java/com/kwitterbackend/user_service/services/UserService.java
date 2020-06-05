package com.kwitterbackend.user_service.services;

import com.kwitterbackend.user_service.AuthUtil;
import com.kwitterbackend.user_service.dto.RegisterDTO;
import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kwitterbackend.user_service.security.UserRole.USER;

@Service
public class UserService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

    public String deleteUser(String username){
        User user = userRepository.findByUsername(username);

        if (userRepository.existsByUsername(username)){
            userRepository.delete(user);
            return "Account deleted";
        }
        return "Already deleted";
    }
    public String deleteAsAdmin(String username) {
        User user = userRepository.findByUsername(username);

        if (userRepository.existsByUsername(username)){
            userRepository.delete(user);
            return "Account deleted";
        }
        return "Already deleted";
    }

    public String changePassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password updated";
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword){
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

}
