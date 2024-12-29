package com.hellspawn287.user.controller;

import com.hellspawn287.user.dao.UserRepository;
import com.hellspawn287.user.model.HistoryMapper;
import com.hellspawn287.user.model.User;
import com.hellspawn287.user.model.UserMapper;
import com.hellspawn287.user.model.dto.HistoryUserDto;
import com.hellspawn287.user.model.dto.UserFullDto;
import com.hellspawn287.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final HistoryMapper historyMapper;

    @GetMapping("/{userId}/history")
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN') || (isAuthenticated() && @authenticationService.hasAccessToUser(#userId))")
    public List<HistoryUserDto<UserFullDto>> getHistory(@PathVariable UUID userId) {

        return userRepository.findRevisions(userId).getContent().stream()
                .map(historyMapper::toDto)
                .collect(Collectors.toList());

    }

    @GetMapping//hasAutority
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Page<UserFullDto> getPageUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getPageUsers(page, size)
                .map(userMapper::mapUserToRecord);
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN') || (isAuthenticated() && @authenticationService.hasAccessToUser(#userId))")
    public UserFullDto getUserById(@PathVariable UUID userId) {
        return userMapper.mapUserToRecord(userService.getById(userId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("isAuthenticated() || hasAuthority('SCOPE_ADMIN')")
    public UserFullDto updateUserById(@PathVariable UUID userId,
                                      @RequestBody @Valid UserFullDto dto) {
        User user = userService.updateUserById(userId, userMapper.mapRecordToUser(dto));
        return userMapper.mapUserToRecord(user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("isAuthenticated() || hasAuthority('SCOPE_ADMIN')")
    public void deleteUserById(@PathVariable UUID userId) {
        userService.delete(userId);
    }
}
