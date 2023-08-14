package com.fontana.backend.session;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private LocalDateTime openedTime;
    private LocalDateTime closedTime;
    private LocalDateTime expirationTime;
    private boolean isForcedToClose;
    private boolean isAutoClosed;

    //TODO list of logs as OneToMany relationship
}