package com.fontana.backend.session.entity;

import com.fontana.backend.log.entity.Log;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "session")
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessionId")
    private List<Log> logs = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "sessionId")
    private List<SessionWatcher> watchers = new ArrayList<>();
}
