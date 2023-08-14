package com.fontana.backend.security.jwt;

import com.fontana.backend.security.TokenType;
import com.fontana.backend.security.blacklist.entity.BlacklistedToken;
import com.fontana.backend.security.blacklist.repository.BlacklistedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-expiration-delay}")
    private String accessExpDelay;

    @Value("${jwt.refresh-expiration-delay}")
    private String refreshExpDelay;

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(String username) {
        return generateToken(new HashMap<>(), username, Long.parseLong(accessExpDelay), TokenType.ACCESS);
    }

    public String generateRefreshToken(String username) {
        return generateToken(new HashMap<>(), username, Long.parseLong(refreshExpDelay), TokenType.REFRESH);
    }

    private String generateToken(Map<String, Object> extraClaims, String username, Long expiration, TokenType tokenType) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exc) {
            throw new JwtExpiredOrUntrustedException(exc.getMessage());
        }
    }

    private boolean isTokenValidWithSecretKey(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException exc) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token) && isTokenValidWithSecretKey(token) && !isTokenBlacklisted(token);
        } catch (JwtException exc) {
            log.error("Invalid token: " + exc.getMessage());
            return false;
        }
    }

    private boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    public void blacklistToken(String token, TokenType tokenType) {
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .tokenType(tokenType)
                .dateAdded(new Date())
                .build();

        blacklistedTokenRepository.save(blacklistedToken);
    }


}
