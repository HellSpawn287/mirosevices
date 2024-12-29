package com.hellspawn287.user.dao;

import com.hellspawn287.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, RevisionRepository<User, UUID, Integer> {
    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);
}
