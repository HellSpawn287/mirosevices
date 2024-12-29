package com.hellspawn287.user.service;

import com.hellspawn287.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserProxyService implements NoSpringUserService{
    private NoSpringUserServiceImpl service;
    private Map<UUID, User> userMap;

    public UserProxyService(NoSpringUserServiceImpl service) {
        this.service = service;
        this.userMap = service.getAllUsers().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));;
    }

    @Override
    public User getById(UUID id) {
//        return userMap.computeIfAbsent(id, service::getById);
        System.out.println("Searching user by id: " + id);
        User user = userMap.get(id);
        if (user != null) {
            System.out.println("User found in user's map");
            return user;
        } else {
            System.out.println("User not found in user's map");
            return userMap.put(id, service.getById(id));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @Override
    public User save(User user) {
        System.out.println("Saving user with id: " + user.getId());
        User saved = service.save(user);
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        } else {
            userMap.replace(user.getId(), user);
        }
        return saved;
    }

    @Override
    public void delete(User user) {
        userMap.remove(user.getId(), user);
    }
}
