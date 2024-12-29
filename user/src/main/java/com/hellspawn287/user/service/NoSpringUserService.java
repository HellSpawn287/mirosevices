package com.hellspawn287.user.service;

import com.hellspawn287.user.model.User;

import java.util.List;
import java.util.UUID;

public interface NoSpringUserService {

    User getById(UUID id);

    List<User> getAllUsers();

    User save(User user);

    void delete(User user);

}