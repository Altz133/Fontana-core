package com.fontana.backend.security.blacklist;

import com.fontana.backend.security.blacklist.repository.BlacklistedTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;
    @Transactional
    @Scheduled(fixedRate = 600000)//co 10 min sprawdza czy sa przedawnione tokeny
    public void cleanupBlacklistedTokens() {
        Date now = new Date();
        blacklistedTokenRepository.deleteByExpirationDateBefore(now);
    }
}

