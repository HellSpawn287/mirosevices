package com.hellspawn287.user.service;

import com.hellspawn287.user.dao.UserRepository;
import com.hellspawn287.user.model.Role;
import com.hellspawn287.user.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ExecutorService executorService;

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public User save(User user) {
        // checking for username exists in a database
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new EntityExistsException(user.getUsername() + " and email: " + user.getEmail());
        }
        // creating user object
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        Optional<Role> optionalRole = roleService.findByName("NEW_USER");

        optionalRole.ifPresent(role -> user.setRoles(Collections.singleton(role)));

        User saved = userRepository.save(user);
        log.info("Safed user :" + user.getEmail());
        emailService.sendingEmailAsync(user.getEmail());
        executorService.execute(() -> emailService.sendingEmail(user.getEmail()));

        return saved;
    }


    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional // spring zrobi update jeśli zajdzie taka potrzeba
    public User updateUserById(UUID userId, User toBeUpdated) {
        User user = getById(userId);
        user.setEmail(toBeUpdated.getEmail());
        user.setUsername(toBeUpdated.getUsername());
        return user;
    }

    public Page<User> getPageUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Revision<Integer, User>> getUserHistoryById(UUID id, Pageable pageable) {
        return userRepository.findRevisions(id, pageable);
    }
}
