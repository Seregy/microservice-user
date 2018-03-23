package com.microservice.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getList();
    Optional<User> getUser(Long id);
    User createUser(String email, String password, String name);
    User updateUser(Long id, User updatedUser);
}
