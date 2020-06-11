package com.kwitterbackend.user_service.controllers;


import com.google.gson.Gson;
import com.kwitterbackend.user_service.dto.RegisterDTO;
import com.kwitterbackend.user_service.dto.UpdateUserEvent;
import com.kwitterbackend.user_service.model.DeleteUserEvent;
import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import com.kwitterbackend.user_service.services.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${kwitter.rabbitmq.exchange}")
    private String exchange;
    @Value("${kwitter.rabbitmq.routingkey}")
    private String routingkey;

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.deleteUser, method = RequestMethod.DELETE)
    public @ResponseBody String delete() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        System.out.println(username);
        DeleteUserEvent deleteUserEvent = new DeleteUserEvent();
        deleteUserEvent.setUsername(username);
        rabbitTemplate.convertAndSend(exchange, routingkey, deleteUserEvent);
        System.out.println("Sent event:" + deleteUserEvent);
        return userService.deleteUser(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = RestURIConstant.deleteAsAdmin, method = RequestMethod.DELETE)
    public @ResponseBody String deleteAsAdmin(@RequestParam("username") String username) {
        return userService.deleteAsAdmin(username);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = RestURIConstant.allUsers, method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> allUsers() {
        return userService.allusers();
    }

    @RequestMapping(value = RestURIConstant.test, method = RequestMethod.GET)
    public String test() {
        return "Test";
    }

    @RequestMapping(value = RestURIConstant.getUser, method = RequestMethod.GET)
    public @ResponseBody User getUserByUsername(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @PostMapping(value = RestURIConstant.register)
    public @ResponseBody
    String userRegister(@RequestBody String user) {
        try {
            Gson gson = new Gson();
            var userObject = gson.fromJson(user, RegisterDTO.class);
            return gson.toJson(userService.registerUser(userObject));
        } catch (Exception e) {
            return ("Failed to register");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.currentUser, method = RequestMethod.GET)
    public @ResponseBody
    User current() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = (String) auth.getPrincipal();

        return userRepository.findByUsername(username);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.updateUsername, method = RequestMethod.PUT)
    public @ResponseBody
    String updateUsername(@RequestParam("username") String username){
        System.out.println("currently logged in user is " + current().getUsername());
        UpdateUserEvent updateUserEvent = new UpdateUserEvent();
        updateUserEvent.setOldUsername(current().getUsername());
        updateUserEvent.setNewUsername(username);
        rabbitTemplate.convertAndSend(exchange, routingkey, updateUserEvent);
        System.out.println("Sent event:" + updateUserEvent);
        return userService.changeUsername(current(), username);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.changePassword, method = RequestMethod.POST)
    public @ResponseBody
    String changePassword(@RequestParam("oldPass") String oldPassword, @RequestParam("newPass") String newPassword) throws Exception {
        if(!userService.checkIfValidOldPassword(current(), oldPassword)){
            return "Old password does not match the system";
        }
        return userService.changePassword(current(), newPassword);
    }

    @RequestMapping(value = RestURIConstant.resetPassword, method = RequestMethod.POST)
    public @ResponseBody
    String resetPassword(@RequestParam("email") String email){
        return null;
    }

}
