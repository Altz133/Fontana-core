package com.fontana.backend.session.dto;

import com.fontana.backend.session.entity.SessionWatcher;
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
public class SessionResponseDTO {

    private Integer id;
    private String username;
    private LocalDateTime openedTime;
    private LocalDateTime closedTime;
    private LocalDateTime expirationTime;
    private boolean isForcedToClose;
    private boolean isAutoClosed;
    private int logsAmount;
    private List<SessionWatcher> watchers = new ArrayList<>();
}
