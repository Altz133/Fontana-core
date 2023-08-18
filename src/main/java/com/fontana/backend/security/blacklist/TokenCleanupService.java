package com.fontana.backend.security.blacklist;

import com.fontana.backend.security.jwt.JwtService;
import com.fontana.backend.security.blacklist.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Scheduled(fixedRate = 600000)//co 10 min sprawdza czy sa przedawnione tokeny
    public void cleanupBlacklistedTokens() {
        Date now = new Date();
        blacklistedTokenRepository.deleteByExpirationDateBefore(now);
    }
}

