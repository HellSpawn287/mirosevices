package com.hellspawn287.user.scheduler;

import com.hellspawn287.user.security.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenScheduler {
    private final TokenRepository tokenRepository;

    @Scheduled(fixedDelay = 60 * 1000)
    public void cleaningTokensFixed() {
        log.info("Clearing tokens fixed delay...");


    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void anotherClearTokens() {
        log.info("Clearing tokens via cron expr...");

        LocalDateTime expDate = LocalDateTime.now().minusDays(1);
        tokenRepository.deleteByCreationDateBefore(expDate);
    }
}
