package com.hellspawn287.user.service;

import com.hellspawn287.user.dao.RoleRepository;
import com.hellspawn287.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role createRole(String roleName) {
        Role role = Role.builder()
                .roleName(roleName)
                .createdBy("Me")
                .creationDate(LocalDateTime.now())
                .build();
        return roleRepository.save(role);
    }
}
