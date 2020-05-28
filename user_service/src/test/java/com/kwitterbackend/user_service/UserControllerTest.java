package com.kwitterbackend.user_service;

import com.kwitterbackend.user_service.controllers.UserController;
import com.kwitterbackend.user_service.dto.RegisterDTO;
import com.kwitterbackend.user_service.repositories.UserRepository;
import com.kwitterbackend.user_service.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    private UserController subject;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    RegisterDTO registerDTO;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        subject = new UserController(userService, userRepository);
    }

//    @Test
//    public void shouldFindAdminAccount() throws Exception {
//        given(userService.getByUsername("admin"))
//                .willReturn(Optional.of(admin))
//    }
    @Test
    public void shouldRegister() throws Exception {
        //TODO: finish test
    }
}
