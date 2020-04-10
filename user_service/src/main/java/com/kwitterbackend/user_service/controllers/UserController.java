package com.kwitterbackend.user_service.controllers;


import com.google.gson.Gson;
import com.kwitterbackend.user_service.dto.RegisterDTO;
import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import com.kwitterbackend.user_service.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value = RestURIConstant.currentUser, method = RequestMethod.GET)
//    public @ResponseBody
//    User current() {
//        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        final String email = (String) auth.getPrincipal();
//        return userRepository.findUserByCustomerCode(email);
//    }

}
