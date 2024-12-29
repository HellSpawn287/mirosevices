package com.hellspawn287.user.service;

import com.hellspawn287.user.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NoSpringUserServiceImpl implements NoSpringUserService {

    private List<User> userList = new ArrayList<>();

    @Override
    public User getById(UUID id) {
        User user = userList.stream()
                .filter(identityUser -> identityUser.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("User not found with ID: " + id.toString())));
        System.out.println("Found user with id: " + id);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User save(User identityUser) {
        HashMap<UUID, User> userMap = getUserMap(); // TODO: wyrzucić cały service do osobnego projektu gdzie nie ma Springa
        User user;
        if (!userMap.containsKey(identityUser.getId())) {
            user = userMap.put(identityUser.getId(), identityUser);
        } else {
            user = userMap.replace(identityUser.getId(), identityUser);
        }
        userList.clear();
        userList.addAll(userMap.values().stream().toList());
        System.out.println("DB size is: " + userList.size());
        return user;
    }

    @Override
    public void delete(User user) {
        HashMap<UUID, User> userMap = getUserMap();
        userMap.remove(user.getId(), user);

        userList = userMap.values().stream().toList();
    }

    @NotNull
    private HashMap<UUID, User> getUserMap() {
        return (HashMap<UUID, User>) userList.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
