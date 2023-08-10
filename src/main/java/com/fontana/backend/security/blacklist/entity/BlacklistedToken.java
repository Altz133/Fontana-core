package com.fontana.backend.security.blacklist.entity;

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

    @Column
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    private Date dateAdded;

    @Column(name = "token_type")
    private String tokenType;
}
