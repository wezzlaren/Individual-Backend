package com.kwitterbackend.user_service.repositories;

import com.kwitterbackend.user_service.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);

}
