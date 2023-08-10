package com.fontana.backend.security.blacklist.repository;

import com.fontana.backend.security.blacklist.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByToken(String token);

    @Modifying
    @Query("DELETE FROM BlacklistedToken b WHERE b.token = ?1")
    void deleteByToken(String token);

    void deleteByDateAddedBefore(Date expiryDate);

    void deleteByDateAddedBeforeAndTokenType(Date date, String tokenType);
}
