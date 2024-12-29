package com.hellspawn287.user.controller;

import com.hellspawn287.user.model.HistoryMapper;
import com.hellspawn287.user.model.User;
import com.hellspawn287.user.model.dto.HistoryUserDto;
import com.hellspawn287.user.model.dto.UserFullDto;
import com.hellspawn287.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {
    public final UserService userService;
    public final HistoryMapper historyMapper;

    @GetMapping("/{id}")
    public Page<HistoryUserDto<UserFullDto>> getUserHistoryById(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        return userService.getUserHistoryById(id, historyMapper.toPageRequest(page, size))
                .map(historyMapper::toDto);
    }
}
