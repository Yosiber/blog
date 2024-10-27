package com.example.my.personal.blog.service;

import com.example.my.personal.blog.persistence.entity.UserEntity;

import java.util.Optional;


public interface UserService {

    void createUser(UserEntity user);

    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> getUserByUsername(String username);
}
