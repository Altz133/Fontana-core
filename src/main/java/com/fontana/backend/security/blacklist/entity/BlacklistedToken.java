package com.fontana.backend.security.blacklist.entity;

import com.fontana.backend.security.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "token_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    private Date dateAdded;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date")
    private Date expirationDate;
}
