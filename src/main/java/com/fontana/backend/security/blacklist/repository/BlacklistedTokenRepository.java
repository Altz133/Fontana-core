package com.fontana.backend.security.blacklist.repository;


import com.fontana.backend.security.blacklist.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByToken(String token);
    void deleteByDateAddedBefore(Date expiryDate);

}
