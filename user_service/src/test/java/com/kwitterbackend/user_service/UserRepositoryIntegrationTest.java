package com.kwitterbackend.user_service;

import com.kwitterbackend.user_service.model.User;
import com.kwitterbackend.user_service.repositories.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.kwitterbackend.user_service.security.UserRole.USER;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository subject;

    @After
    public void tearDown() throws Exception {
        subject.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchUser() throws Exception {
        User user = new User("Test", "test1","testing123@test.com","Test","User",
                true,true,true,true, USER.getGrantedAuthorities());
        subject.save(user);

        User user1 = subject.findByUsername("Test");

        assertThat(user1.getUsername(), equalTo("Test"));
    }

    @Test
    public void shouldFindAllUsers(){
        var users = (List<User>) subject.findAll();
        assertNotNull(users);
    }
}
