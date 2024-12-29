package com.hellspawn287.user.security.repository;

import com.hellspawn287.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    boolean existsByToken(String token);

    void deleteByCreationDateBefore(LocalDateTime expiryDate);

}
