package com.hellspawn287.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Async
    public void sendingEmailAsync(String email){
        log.info("Sending email by Spring Async");

    }

    public void sendingEmail(String email){
        log.info("Sending email by Executors");

    }
}
