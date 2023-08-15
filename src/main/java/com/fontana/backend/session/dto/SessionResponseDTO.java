package com.fontana.backend.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
